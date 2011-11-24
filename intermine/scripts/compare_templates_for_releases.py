#!/usr/bin/python

import sys
import time
import traceback
import smtplib
from email.mime.text import MIMEText
from collections import defaultdict
from intermine.webservice import Service

#### CONSTANTS ####

usage = """
%s: Compare templates from two versions of the same webservice

usage: %s www.flymine.org/query beta.flymine.org/beta ["email@to"] ["email@from"]

All arguments are positional. The last two are optional.

Arguments:
    * service version A
    * service version B
    * an email address to send email to (optional - print to std out if not present)
    * an email to mark as the sender (optional - defaults to the first email address)

"""

SUBJECT = "The results of comparison between %s and %s at %s" 
BODY = """
Template comparison run complete. 

The template comparison run you requested between {rel_a} 
and {rel_b} has been completed at {time}.
The results are as follows:

"""

rfc822_specials = '()<>@,;:\\"[]'

#### ROUTINES ####

def compare_templates(url_a, url_b, send_to=None, send_from=None):
    """Main program logic"""

    if send_from is None:
        send_from = send_to
    if send_to is not None:
        if not isAddressValid(send_to) or not isAddressValid(send_from):
            raise Exception("Invalid email addresses: '%s', '%s'" % (send_to, send_from))

    results = fetch_results(url_a, url_b)
    report_results(results, send_to, send_from)

def fetch_results(url_a, url_b):
    try: 
        services = map(Service, [url_a, url_b])
    except Exception as e:
        raise Exception("Invalid service urls: '%s', '%s'\n" % (url_a, url_b) + str(e))

    results = {
        "failures_from": dict(( (service.release, {}) for service in services)),
        "rows_from": dict(( (service.release, {}) for service in services))
    }

    start = time.time()

    for service in services:
        for name in service.templates.keys():
            try:
                template = service.get_template(name)
            except Exception as e:
                results["failures_from"][service.release][template.name] = str(e)

            print "Querying %s for results for %s" % (service.release, name)
            try: 
                c = template.count()
                results["rows_from"][service.release][template.name] = c
            except Exception as e:
                results["failures_from"][service.release][template.name] = str(e)

    end = time.time()
    total = end - start
    print "Finished fetching results: that took %d min, %d secs" % (total / 60, total % 60)
    return results

def report_results(results, send_to, send_from):
    """Handle the results"""
    body = create_message_body(results)
    print body
    if send_to is not None:
        print "Sending email to %s" % send_to
        msg = MIMEText(body)
        rel_a, rel_b = results["rows_from"].keys()
        msg['Subject'] = SUBJECT % (rel_a, rel_b, time.ctime(time.time()))
        msg['From'] = send_from
        msg['To'] = send_to
        smtp = smtplib.SMTP('localhost')
        smtp.sendmail(send_from, [send_to], msg.as_string())
        smtp.quit()

def create_message_body(results):
    """Analyse the data and present it as a string"""
    rel_a, rel_b = results["rows_from"].keys()
    body = BODY.format(rel_a=rel_a, rel_b=rel_b, time=time.ctime(time.time()))

    body += "\nFAILURES:\n"
    failures_from = results['failures_from']
    for rel, failures in failures_from.items():
        body += (rel + "\n").ljust(80, "=")  + "\n"
        for name, reason in failures.items():
            body += "%s: %s\n" % (name, reason)

    body += "\nBY TEMPLATE:\n"
    successes_from = results["rows_from"]
    template_results = {}
    longest_template_name = 0
    for rel, successes in successes_from.items():
        for name, count in successes.items():
            if name not in template_results:
                template_results[name] = defaultdict(lambda: 0) 
            if len(name) > longest_template_name:
                longest_template_name = len(name)
            template_results[name][rel] = count

    fmt = "%-" + str(longest_template_name) + "s | %-6s | %-6s | %s\n" 
    body += fmt % ("NAME", rel_a, rel_b, "CATEGORY")
    body += "".ljust(100, "-") + "\n"

    fmt = "%-" + str(longest_template_name) + "s | %6d | %6d | %s\n" 
    for name, results_by_rel in template_results.items():
        diff = abs(reduce(lambda x, y: x - y, results_by_rel.values()))
        max_c = max(results_by_rel.values())

        if diff == 0:
            category = "SAME"
        else: 
            proportion = float(diff) / float(max_c)
            if proportion < 0.1:
                category = "CLOSE"
            elif proportion < 0.5:
                category = "DIFFERENT"
            else: 
                category = "VERY DIFFERENT"

        body += fmt % (name, results_by_rel[rel_a], results_by_rel[rel_b], category)
    
    print body

    body += "\nALL SUCCESSES:\n"
    fmt = "%-" + str(longest_template_name) + "s | %6d\n"
    for rel, successes in successes_from.items():
        body += "\n" + (rel + "\n").ljust(80, "=")  + "\n"
        for name, count in successes.items():
            body += fmt % (name, count)

    print body
    return body

def isAddressValid(addr):
    """Check that Email addresses are valid"""
    # First we validate the name portion (name@domain)
    c = 0
    while c < len(addr):
        if addr[c] == '"' and (not c or addr[c - 1] == '.' or addr[c - 1] == '"'):
            c = c + 1
            while c < len(addr):
                if addr[c] == '"': break
                if addr[c] == '\\' and addr[c + 1] == ' ':
                    c = c + 2
                    continue
                if ord(addr[c]) < 32 or ord(addr[c]) >= 127: return 0
                c = c + 1
            else: return 0
            if addr[c] == '@': break
            if addr[c] != '.': return 0
            c = c + 1
            continue
        if addr[c] == '@': break
        if ord(addr[c]) <= 32 or ord(addr[c]) >= 127: return 0
        if addr[c] in rfc822_specials: return 0
        c = c + 1
    if not c or addr[c - 1] == '.': return 0

    # Next we validate the domain portion (name@domain)
    domain = c = c + 1
    if domain >= len(addr): return 0
    count = 0
    while c < len(addr):
        if addr[c] == '.':
            if c == domain or addr[c - 1] == '.': return 0
            count = count + 1
        if ord(addr[c]) <= 32 or ord(addr[c]) >= 127: return 0
        if addr[c] in rfc822_specials: return 0
        c = c + 1

    return count >= 1

#### CALL MAIN ####

if __name__ == "__main__":

    args = sys.argv[1:]

    try: 
        compare_templates(*args)
    except:
        tb = traceback.format_exc()
        print tb
        print usage % (sys.argv[0], sys.argv[0])
        exit(1)

