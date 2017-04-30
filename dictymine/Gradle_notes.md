# Ant tasks
## dbmodel

`-init-project`
Set `project.properties` file. **No**

`-check-for-intermine-properties-file`
Need to set the `intermine.properties.file` property in your `project.properties`  **No**

`-check-for-intermine-properties-dir`
Check if `${HOME}/.intermine` folder exists. **No**

`-init-check-for-intermine-properties-dir`
Sets the property for `${HOME}/.intermine` folder. **No**

`-set-release-suffix-not-exists`
Sets the `release-suffix` property to empty value **No**

`-set-release-suffix-exists`
Sets the `release-suffix` property to `release` variable **No**

`-set-release-suffix`
Depends on the previous two tasks **No**
