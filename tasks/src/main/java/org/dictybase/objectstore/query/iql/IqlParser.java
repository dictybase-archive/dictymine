// $ANTLR : "dictybase_iql.g" -> "IqlParser.java"$

package org.dictybase.objectstore.query.iql;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class IqlParser extends antlr.LLkParser       implements IqlTokenTypes
 {

protected IqlParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public IqlParser(TokenBuffer tokenBuf) {
  this(tokenBuf,7);
}

protected IqlParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public IqlParser(TokenStream lexer) {
  this(lexer,7);
}

public IqlParser(ParserSharedInputState state) {
  super(state,7);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void start_rule() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST start_rule_AST = null;

		iql_statement();
		astFactory.addASTChild(currentAST, returnAST);
		start_rule_AST = (AST)currentAST.root;
		returnAST = start_rule_AST;
	}

	public final void iql_statement() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST iql_statement_AST = null;

		select_command();
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			iql_statement_AST = (AST)currentAST.root;
			iql_statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(IQL_STATEMENT,"IQL_STATEMENT")).add(iql_statement_AST));
			currentAST.root = iql_statement_AST;
			currentAST.child = iql_statement_AST!=null &&iql_statement_AST.getFirstChild()!=null ?
				iql_statement_AST.getFirstChild() : iql_statement_AST;
			currentAST.advanceChildToEnd();
		}
		iql_statement_AST = (AST)currentAST.root;
		returnAST = iql_statement_AST;
	}

	public final void select_command() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST select_command_AST = null;

		{
		switch ( LA(1)) {
		case LITERAL_explain:
		{
			AST tmp1_AST = null;
			tmp1_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp1_AST);
			match(LITERAL_explain);
			break;
		}
		case LITERAL_select:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(LITERAL_select);
		{
		switch ( LA(1)) {
		case LITERAL_all:
		{
			match(LITERAL_all);
			break;
		}
		case LITERAL_distinct:
		{
			AST tmp4_AST = null;
			tmp4_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp4_AST);
			match(LITERAL_distinct);
			break;
		}
		case IDENTIFIER:
		case OPEN_PAREN:
		case LITERAL_bag:
		case INTEGER:
		case LITERAL_bags:
		case QUOTED_STRING:
		case FLOAT:
		case LITERAL_true:
		case LITERAL_false:
		case LITERAL_null:
		case LITERAL_count:
		case LITERAL_max:
		case LITERAL_min:
		case LITERAL_sum:
		case LITERAL_avg:
		case LITERAL_substr:
		case LITERAL_indexof:
		case LITERAL_lower:
		case LITERAL_upper:
		case LITERAL_stddev:
		case LITERAL_greatest:
		case LITERAL_least:
		case LITERAL_ceil:
		case LITERAL_floor:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		select_list();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case LITERAL_from:
		{
			from_list();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case EOF:
		case LITERAL_where:
		case LITERAL_group:
		case LITERAL_order:
		case CLOSE_PAREN:
		case LITERAL_limit:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case LITERAL_where:
		{
			where_clause();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case EOF:
		case LITERAL_group:
		case LITERAL_order:
		case CLOSE_PAREN:
		case LITERAL_limit:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case LITERAL_group:
		{
			group_clause();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case EOF:
		case LITERAL_order:
		case CLOSE_PAREN:
		case LITERAL_limit:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case LITERAL_order:
		{
			order_clause();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case EOF:
		case CLOSE_PAREN:
		case LITERAL_limit:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		select_command_AST = (AST)currentAST.root;
		returnAST = select_command_AST;
	}

	public final void select_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST select_list_AST = null;

		select_value();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop459:
		do {
			if ((LA(1)==COMMA)) {
				match(COMMA);
				select_value();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop459;
			}

		} while (true);
		}
		if ( inputState.guessing==0 ) {
			select_list_AST = (AST)currentAST.root;
			select_list_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SELECT_LIST,"SELECT_LIST")).add(select_list_AST));
			currentAST.root = select_list_AST;
			currentAST.child = select_list_AST!=null &&select_list_AST.getFirstChild()!=null ?
				select_list_AST.getFirstChild() : select_list_AST;
			currentAST.advanceChildToEnd();
		}
		select_list_AST = (AST)currentAST.root;
		returnAST = select_list_AST;
	}

	public final void from_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST from_list_AST = null;

		match(LITERAL_from);
		abstract_table();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop462:
		do {
			if ((LA(1)==COMMA)) {
				match(COMMA);
				abstract_table();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop462;
			}

		} while (true);
		}
		if ( inputState.guessing==0 ) {
			from_list_AST = (AST)currentAST.root;
			from_list_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FROM_LIST,"FROM_LIST")).add(from_list_AST));
			currentAST.root = from_list_AST;
			currentAST.child = from_list_AST!=null &&from_list_AST.getFirstChild()!=null ?
				from_list_AST.getFirstChild() : from_list_AST;
			currentAST.advanceChildToEnd();
		}
		from_list_AST = (AST)currentAST.root;
		returnAST = from_list_AST;
	}

	public final void where_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST where_clause_AST = null;

		match(LITERAL_where);
		abstract_constraint();
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			where_clause_AST = (AST)currentAST.root;
			where_clause_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(WHERE_CLAUSE,"WHERE_CLAUSE")).add(where_clause_AST));
			currentAST.root = where_clause_AST;
			currentAST.child = where_clause_AST!=null &&where_clause_AST.getFirstChild()!=null ?
				where_clause_AST.getFirstChild() : where_clause_AST;
			currentAST.advanceChildToEnd();
		}
		where_clause_AST = (AST)currentAST.root;
		returnAST = where_clause_AST;
	}

	public final void group_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST group_clause_AST = null;

		match(LITERAL_group);
		match(LITERAL_by);
		abstract_value();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop466:
		do {
			if ((LA(1)==COMMA)) {
				match(COMMA);
				abstract_value();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop466;
			}

		} while (true);
		}
		if ( inputState.guessing==0 ) {
			group_clause_AST = (AST)currentAST.root;
			group_clause_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(GROUP_CLAUSE,"GROUP_CLAUSE")).add(group_clause_AST));
			currentAST.root = group_clause_AST;
			currentAST.child = group_clause_AST!=null &&group_clause_AST.getFirstChild()!=null ?
				group_clause_AST.getFirstChild() : group_clause_AST;
			currentAST.advanceChildToEnd();
		}
		group_clause_AST = (AST)currentAST.root;
		returnAST = group_clause_AST;
	}

	public final void order_clause() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST order_clause_AST = null;

		match(LITERAL_order);
		match(LITERAL_by);
		orderby_value();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop469:
		do {
			if ((LA(1)==COMMA)) {
				match(COMMA);
				orderby_value();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop469;
			}

		} while (true);
		}
		if ( inputState.guessing==0 ) {
			order_clause_AST = (AST)currentAST.root;
			order_clause_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ORDER_CLAUSE,"ORDER_CLAUSE")).add(order_clause_AST));
			currentAST.root = order_clause_AST;
			currentAST.child = order_clause_AST!=null &&order_clause_AST.getFirstChild()!=null ?
				order_clause_AST.getFirstChild() : order_clause_AST;
			currentAST.advanceChildToEnd();
		}
		order_clause_AST = (AST)currentAST.root;
		returnAST = order_clause_AST;
	}

	public final void select_value() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST select_value_AST = null;

		{
		switch ( LA(1)) {
		case LITERAL_bag:
		{
			objectstorebag();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop481:
			do {
				if (((LA(1) >= LITERAL_union && LA(1) <= LITERAL_allbutintersect))) {
					{
					switch ( LA(1)) {
					case LITERAL_union:
					{
						AST tmp15_AST = null;
						tmp15_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp15_AST);
						match(LITERAL_union);
						break;
					}
					case LITERAL_intersect:
					{
						AST tmp16_AST = null;
						tmp16_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp16_AST);
						match(LITERAL_intersect);
						break;
					}
					case LITERAL_except:
					{
						AST tmp17_AST = null;
						tmp17_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp17_AST);
						match(LITERAL_except);
						break;
					}
					case LITERAL_allbutintersect:
					{
						AST tmp18_AST = null;
						tmp18_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp18_AST);
						match(LITERAL_allbutintersect);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					objectstorebag();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop481;
				}

			} while (true);
			}
			break;
		}
		case LITERAL_bags:
		{
			bags_for();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		default:
			boolean synPredMatched473 = false;
			if (((_tokenSet_0.member(LA(1))) && (_tokenSet_1.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_3.member(LA(4))) && (_tokenSet_4.member(LA(5))) && (_tokenSet_5.member(LA(6))) && (_tokenSet_6.member(LA(7))))) {
				int _m473 = mark();
				synPredMatched473 = true;
				inputState.guessing++;
				try {
					{
					unsafe_function();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched473 = false;
				}
				rewind(_m473);
inputState.guessing--;
			}
			if ( synPredMatched473 ) {
				unsafe_function();
				astFactory.addASTChild(currentAST, returnAST);
				match(LITERAL_as);
				field_alias();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				boolean synPredMatched475 = false;
				if (((_tokenSet_0.member(LA(1))) && (_tokenSet_7.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_3.member(LA(4))) && (_tokenSet_4.member(LA(5))) && (_tokenSet_5.member(LA(6))) && (_tokenSet_6.member(LA(7))))) {
					int _m475 = mark();
					synPredMatched475 = true;
					inputState.guessing++;
					try {
						{
						typecast();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched475 = false;
					}
					rewind(_m475);
inputState.guessing--;
				}
				if ( synPredMatched475 ) {
					typecast();
					astFactory.addASTChild(currentAST, returnAST);
					match(LITERAL_as);
					field_alias();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					boolean synPredMatched477 = false;
					if (((LA(1)==IDENTIFIER) && (LA(2)==DOT) && (LA(3)==IDENTIFIER) && (LA(4)==OPEN_PAREN) && (_tokenSet_8.member(LA(5))) && (_tokenSet_9.member(LA(6))) && (_tokenSet_10.member(LA(7))))) {
						int _m477 = mark();
						synPredMatched477 = true;
						inputState.guessing++;
						try {
							{
							match(IDENTIFIER);
							match(DOT);
							match(IDENTIFIER);
							match(OPEN_PAREN);
							}
						}
						catch (RecognitionException pe) {
							synPredMatched477 = false;
						}
						rewind(_m477);
inputState.guessing--;
					}
					if ( synPredMatched477 ) {
						collection_path_expression();
						astFactory.addASTChild(currentAST, returnAST);
						match(LITERAL_as);
						field_alias();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else if ((LA(1)==IDENTIFIER) && (_tokenSet_11.member(LA(2))) && (_tokenSet_12.member(LA(3))) && (_tokenSet_13.member(LA(4))) && (_tokenSet_14.member(LA(5))) && (_tokenSet_15.member(LA(6))) && (_tokenSet_16.member(LA(7)))) {
						thing();
						astFactory.addASTChild(currentAST, returnAST);
						{
						switch ( LA(1)) {
						case LITERAL_as:
						{
							match(LITERAL_as);
							field_alias();
							astFactory.addASTChild(currentAST, returnAST);
							break;
						}
						case EOF:
						case COMMA:
						case LITERAL_from:
						case LITERAL_where:
						case LITERAL_group:
						case LITERAL_order:
						case CLOSE_PAREN:
						case LITERAL_limit:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
					}
					else if ((_tokenSet_17.member(LA(1))) && (LA(2)==LITERAL_as)) {
						constant();
						astFactory.addASTChild(currentAST, returnAST);
						match(LITERAL_as);
						field_alias();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else if ((_tokenSet_18.member(LA(1))) && (LA(2)==OPEN_PAREN) && (_tokenSet_19.member(LA(3))) && (_tokenSet_4.member(LA(4))) && (_tokenSet_20.member(LA(5))) && (_tokenSet_4.member(LA(6))) && (_tokenSet_5.member(LA(7)))) {
						safe_function();
						astFactory.addASTChild(currentAST, returnAST);
						match(LITERAL_as);
						field_alias();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else if ((LA(1)==OPEN_PAREN) && (_tokenSet_0.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_20.member(LA(4))) && (_tokenSet_4.member(LA(5))) && (_tokenSet_5.member(LA(6))) && (_tokenSet_6.member(LA(7)))) {
						paren_value();
						astFactory.addASTChild(currentAST, returnAST);
						match(LITERAL_as);
						field_alias();
						astFactory.addASTChild(currentAST, returnAST);
					}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}}}
				}
				if ( inputState.guessing==0 ) {
					select_value_AST = (AST)currentAST.root;
					select_value_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SELECT_VALUE,"SELECT_VALUE")).add(select_value_AST));
					currentAST.root = select_value_AST;
					currentAST.child = select_value_AST!=null &&select_value_AST.getFirstChild()!=null ?
						select_value_AST.getFirstChild() : select_value_AST;
					currentAST.advanceChildToEnd();
				}
				select_value_AST = (AST)currentAST.root;
				returnAST = select_value_AST;
			}

	public final void abstract_table() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST abstract_table_AST = null;

		if ((LA(1)==IDENTIFIER)) {
			table();
			astFactory.addASTChild(currentAST, returnAST);
			abstract_table_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==OPEN_PAREN) && (LA(2)==LITERAL_explain||LA(2)==LITERAL_select)) {
			subquery();
			astFactory.addASTChild(currentAST, returnAST);
			abstract_table_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==OPEN_PAREN) && (LA(2)==IDENTIFIER)) {
			multitable();
			astFactory.addASTChild(currentAST, returnAST);
			abstract_table_AST = (AST)currentAST.root;
		}
		else {
			boolean synPredMatched484 = false;
			if (((LA(1)==LITERAL_bag||LA(1)==QUESTION_MARK) && (LA(2)==OPEN_PAREN||LA(2)==COLONTYPE) && (LA(3)==IDENTIFIER||LA(3)==INTEGER) && (LA(4)==LITERAL_as||LA(4)==DOT||LA(4)==CLOSE_PAREN) && (LA(5)==IDENTIFIER||LA(5)==COLONTYPE) && (_tokenSet_21.member(LA(6))) && (_tokenSet_22.member(LA(7))))) {
				int _m484 = mark();
				synPredMatched484 = true;
				inputState.guessing++;
				try {
					{
					query_class_bag();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched484 = false;
				}
				rewind(_m484);
inputState.guessing--;
			}
			if ( synPredMatched484 ) {
				query_class_bag();
				astFactory.addASTChild(currentAST, returnAST);
				abstract_table_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==LITERAL_bag||LA(1)==QUESTION_MARK) && (LA(2)==OPEN_PAREN||LA(2)==COLONTYPE) && (LA(3)==OPEN_PAREN||LA(3)==INTEGER) && (LA(4)==IDENTIFIER||LA(4)==CLOSE_PAREN) && (_tokenSet_23.member(LA(5))) && (LA(6)==LITERAL_as||LA(6)==IDENTIFIER||LA(6)==OPEN_PAREN) && (_tokenSet_24.member(LA(7)))) {
				query_class_bag_multi();
				astFactory.addASTChild(currentAST, returnAST);
				abstract_table_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			returnAST = abstract_table_AST;
		}

	public final void abstract_constraint() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST abstract_constraint_AST = null;

		boolean synPredMatched565 = false;
		if (((_tokenSet_25.member(LA(1))) && (_tokenSet_26.member(LA(2))) && (_tokenSet_27.member(LA(3))) && (_tokenSet_28.member(LA(4))) && (_tokenSet_29.member(LA(5))) && (_tokenSet_29.member(LA(6))) && (_tokenSet_15.member(LA(7))))) {
			int _m565 = mark();
			synPredMatched565 = true;
			inputState.guessing++;
			try {
				{
				constraint_set();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched565 = false;
			}
			rewind(_m565);
inputState.guessing--;
		}
		if ( synPredMatched565 ) {
			constraint_set();
			astFactory.addASTChild(currentAST, returnAST);
			abstract_constraint_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_25.member(LA(1))) && (_tokenSet_30.member(LA(2))) && (_tokenSet_31.member(LA(3))) && (_tokenSet_32.member(LA(4))) && (_tokenSet_15.member(LA(5))) && (_tokenSet_15.member(LA(6))) && (_tokenSet_15.member(LA(7)))) {
			safe_abstract_constraint();
			astFactory.addASTChild(currentAST, returnAST);
			abstract_constraint_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = abstract_constraint_AST;
	}

	public final void abstract_value() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST abstract_value_AST = null;

		boolean synPredMatched490 = false;
		if (((_tokenSet_0.member(LA(1))) && (_tokenSet_1.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_33.member(LA(4))) && (_tokenSet_34.member(LA(5))) && (_tokenSet_35.member(LA(6))) && (_tokenSet_14.member(LA(7))))) {
			int _m490 = mark();
			synPredMatched490 = true;
			inputState.guessing++;
			try {
				{
				unsafe_function();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched490 = false;
			}
			rewind(_m490);
inputState.guessing--;
		}
		if ( synPredMatched490 ) {
			unsafe_function();
			astFactory.addASTChild(currentAST, returnAST);
			abstract_value_AST = (AST)currentAST.root;
		}
		else {
			boolean synPredMatched492 = false;
			if (((_tokenSet_0.member(LA(1))) && (_tokenSet_7.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_33.member(LA(4))) && (_tokenSet_34.member(LA(5))) && (_tokenSet_35.member(LA(6))) && (_tokenSet_14.member(LA(7))))) {
				int _m492 = mark();
				synPredMatched492 = true;
				inputState.guessing++;
				try {
					{
					typecast();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched492 = false;
				}
				rewind(_m492);
inputState.guessing--;
			}
			if ( synPredMatched492 ) {
				typecast();
				astFactory.addASTChild(currentAST, returnAST);
				abstract_value_AST = (AST)currentAST.root;
			}
			else if ((_tokenSet_17.member(LA(1))) && (_tokenSet_36.member(LA(2)))) {
				constant();
				astFactory.addASTChild(currentAST, returnAST);
				abstract_value_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==IDENTIFIER) && (_tokenSet_37.member(LA(2))) && (_tokenSet_38.member(LA(3))) && (_tokenSet_35.member(LA(4))) && (_tokenSet_14.member(LA(5))) && (_tokenSet_15.member(LA(6))) && (_tokenSet_15.member(LA(7)))) {
				thing();
				astFactory.addASTChild(currentAST, returnAST);
				abstract_value_AST = (AST)currentAST.root;
			}
			else if ((_tokenSet_18.member(LA(1))) && (LA(2)==OPEN_PAREN) && (_tokenSet_19.member(LA(3))) && (_tokenSet_4.member(LA(4))) && (_tokenSet_33.member(LA(5))) && (_tokenSet_34.member(LA(6))) && (_tokenSet_35.member(LA(7)))) {
				safe_function();
				astFactory.addASTChild(currentAST, returnAST);
				abstract_value_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==OPEN_PAREN) && (_tokenSet_0.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_33.member(LA(4))) && (_tokenSet_34.member(LA(5))) && (_tokenSet_35.member(LA(6))) && (_tokenSet_14.member(LA(7)))) {
				paren_value();
				astFactory.addASTChild(currentAST, returnAST);
				abstract_value_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			returnAST = abstract_value_AST;
		}

	public final void orderby_value() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST orderby_value_AST = null;

		boolean synPredMatched487 = false;
		if (((_tokenSet_0.member(LA(1))) && (_tokenSet_39.member(LA(2))) && (_tokenSet_40.member(LA(3))) && (_tokenSet_41.member(LA(4))) && (_tokenSet_42.member(LA(5))) && (_tokenSet_43.member(LA(6))) && (_tokenSet_44.member(LA(7))))) {
			int _m487 = mark();
			synPredMatched487 = true;
			inputState.guessing++;
			try {
				{
				abstract_value();
				match(LITERAL_desc);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched487 = false;
			}
			rewind(_m487);
inputState.guessing--;
		}
		if ( synPredMatched487 ) {
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(LITERAL_desc);
			if ( inputState.guessing==0 ) {
				orderby_value_AST = (AST)currentAST.root;
				orderby_value_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ORDER_DESC,"ORDER_DESC")).add(orderby_value_AST));
				currentAST.root = orderby_value_AST;
				currentAST.child = orderby_value_AST!=null &&orderby_value_AST.getFirstChild()!=null ?
					orderby_value_AST.getFirstChild() : orderby_value_AST;
				currentAST.advanceChildToEnd();
			}
			orderby_value_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_0.member(LA(1))) && (_tokenSet_40.member(LA(2))) && (_tokenSet_45.member(LA(3))) && (_tokenSet_42.member(LA(4))) && (_tokenSet_46.member(LA(5))) && (_tokenSet_44.member(LA(6))) && (_tokenSet_47.member(LA(7)))) {
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			orderby_value_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = orderby_value_AST;
	}

	public final void unsafe_function() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unsafe_function_AST = null;

		{
		boolean synPredMatched551 = false;
		if (((_tokenSet_0.member(LA(1))) && (_tokenSet_48.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_49.member(LA(4))) && (_tokenSet_34.member(LA(5))) && (_tokenSet_35.member(LA(6))) && (_tokenSet_14.member(LA(7))))) {
			int _m551 = mark();
			synPredMatched551 = true;
			inputState.guessing++;
			try {
				{
				safe_abstract_value();
				match(PLUS);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched551 = false;
			}
			rewind(_m551);
inputState.guessing--;
		}
		if ( synPredMatched551 ) {
			safe_abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp27_AST = null;
			tmp27_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp27_AST);
			match(PLUS);
			safe_abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else {
			boolean synPredMatched553 = false;
			if (((_tokenSet_0.member(LA(1))) && (_tokenSet_50.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_49.member(LA(4))) && (_tokenSet_34.member(LA(5))) && (_tokenSet_35.member(LA(6))) && (_tokenSet_14.member(LA(7))))) {
				int _m553 = mark();
				synPredMatched553 = true;
				inputState.guessing++;
				try {
					{
					safe_abstract_value();
					match(PERCENT);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched553 = false;
				}
				rewind(_m553);
inputState.guessing--;
			}
			if ( synPredMatched553 ) {
				safe_abstract_value();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp28_AST = null;
				tmp28_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp28_AST);
				match(PERCENT);
				safe_abstract_value();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				boolean synPredMatched555 = false;
				if (((_tokenSet_0.member(LA(1))) && (_tokenSet_51.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_49.member(LA(4))) && (_tokenSet_34.member(LA(5))) && (_tokenSet_35.member(LA(6))) && (_tokenSet_14.member(LA(7))))) {
					int _m555 = mark();
					synPredMatched555 = true;
					inputState.guessing++;
					try {
						{
						safe_abstract_value();
						match(ASTERISK);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched555 = false;
					}
					rewind(_m555);
inputState.guessing--;
				}
				if ( synPredMatched555 ) {
					safe_abstract_value();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp29_AST = null;
					tmp29_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp29_AST);
					match(ASTERISK);
					safe_abstract_value();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					boolean synPredMatched557 = false;
					if (((_tokenSet_0.member(LA(1))) && (_tokenSet_52.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_49.member(LA(4))) && (_tokenSet_34.member(LA(5))) && (_tokenSet_35.member(LA(6))) && (_tokenSet_14.member(LA(7))))) {
						int _m557 = mark();
						synPredMatched557 = true;
						inputState.guessing++;
						try {
							{
							safe_abstract_value();
							match(DIVIDE);
							}
						}
						catch (RecognitionException pe) {
							synPredMatched557 = false;
						}
						rewind(_m557);
inputState.guessing--;
					}
					if ( synPredMatched557 ) {
						safe_abstract_value();
						astFactory.addASTChild(currentAST, returnAST);
						AST tmp30_AST = null;
						tmp30_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp30_AST);
						match(DIVIDE);
						safe_abstract_value();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						boolean synPredMatched559 = false;
						if (((_tokenSet_0.member(LA(1))) && (_tokenSet_53.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_49.member(LA(4))) && (_tokenSet_34.member(LA(5))) && (_tokenSet_35.member(LA(6))) && (_tokenSet_14.member(LA(7))))) {
							int _m559 = mark();
							synPredMatched559 = true;
							inputState.guessing++;
							try {
								{
								safe_abstract_value();
								match(POWER);
								}
							}
							catch (RecognitionException pe) {
								synPredMatched559 = false;
							}
							rewind(_m559);
inputState.guessing--;
						}
						if ( synPredMatched559 ) {
							safe_abstract_value();
							astFactory.addASTChild(currentAST, returnAST);
							AST tmp31_AST = null;
							tmp31_AST = astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp31_AST);
							match(POWER);
							safe_abstract_value();
							astFactory.addASTChild(currentAST, returnAST);
						}
						else {
							boolean synPredMatched561 = false;
							if (((_tokenSet_0.member(LA(1))) && (_tokenSet_54.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_49.member(LA(4))) && (_tokenSet_34.member(LA(5))) && (_tokenSet_35.member(LA(6))) && (_tokenSet_14.member(LA(7))))) {
								int _m561 = mark();
								synPredMatched561 = true;
								inputState.guessing++;
								try {
									{
									safe_abstract_value();
									match(MINUS);
									}
								}
								catch (RecognitionException pe) {
									synPredMatched561 = false;
								}
								rewind(_m561);
inputState.guessing--;
							}
							if ( synPredMatched561 ) {
								safe_abstract_value();
								astFactory.addASTChild(currentAST, returnAST);
								AST tmp32_AST = null;
								tmp32_AST = astFactory.create(LT(1));
								astFactory.addASTChild(currentAST, tmp32_AST);
								match(MINUS);
								safe_abstract_value();
								astFactory.addASTChild(currentAST, returnAST);
							}
							else {
								throw new NoViableAltException(LT(1), getFilename());
							}
							}}}}}
							}
							if ( inputState.guessing==0 ) {
								unsafe_function_AST = (AST)currentAST.root;
								unsafe_function_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(UNSAFE_FUNCTION,"UNSAFE_FUNCTION")).add(unsafe_function_AST));
								currentAST.root = unsafe_function_AST;
								currentAST.child = unsafe_function_AST!=null &&unsafe_function_AST.getFirstChild()!=null ?
									unsafe_function_AST.getFirstChild() : unsafe_function_AST;
								currentAST.advanceChildToEnd();
							}
							unsafe_function_AST = (AST)currentAST.root;
							returnAST = unsafe_function_AST;
						}

	public final void field_alias() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST field_alias_AST = null;

		AST tmp33_AST = null;
		tmp33_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp33_AST);
		match(IDENTIFIER);
		if ( inputState.guessing==0 ) {
			field_alias_AST = (AST)currentAST.root;
			field_alias_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FIELD_ALIAS,"FIELD_ALIAS")).add(field_alias_AST));
			currentAST.root = field_alias_AST;
			currentAST.child = field_alias_AST!=null &&field_alias_AST.getFirstChild()!=null ?
				field_alias_AST.getFirstChild() : field_alias_AST;
			currentAST.advanceChildToEnd();
		}
		field_alias_AST = (AST)currentAST.root;
		returnAST = field_alias_AST;
	}

	public final void typecast() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST typecast_AST = null;

		{
		switch ( LA(1)) {
		case INTEGER:
		case QUOTED_STRING:
		case FLOAT:
		case LITERAL_true:
		case LITERAL_false:
		case LITERAL_null:
		{
			constant();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case IDENTIFIER:
		{
			thing();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case LITERAL_count:
		case LITERAL_max:
		case LITERAL_min:
		case LITERAL_sum:
		case LITERAL_avg:
		case LITERAL_substr:
		case LITERAL_indexof:
		case LITERAL_lower:
		case LITERAL_upper:
		case LITERAL_stddev:
		case LITERAL_greatest:
		case LITERAL_least:
		case LITERAL_ceil:
		case LITERAL_floor:
		{
			safe_function();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case OPEN_PAREN:
		{
			paren_value();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(COLONTYPE);
		AST tmp35_AST = null;
		tmp35_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp35_AST);
		match(IDENTIFIER);
		if ( inputState.guessing==0 ) {
			typecast_AST = (AST)currentAST.root;
			typecast_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TYPECAST,"TYPECAST")).add(typecast_AST));
			currentAST.root = typecast_AST;
			currentAST.child = typecast_AST!=null &&typecast_AST.getFirstChild()!=null ?
				typecast_AST.getFirstChild() : typecast_AST;
			currentAST.advanceChildToEnd();
		}
		typecast_AST = (AST)currentAST.root;
		returnAST = typecast_AST;
	}

	public final void collection_path_expression() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST collection_path_expression_AST = null;

		AST tmp36_AST = null;
		tmp36_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp36_AST);
		match(IDENTIFIER);
		match(DOT);
		AST tmp38_AST = null;
		tmp38_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp38_AST);
		match(IDENTIFIER);
		match(OPEN_PAREN);
		{
		switch ( LA(1)) {
		case LITERAL_select:
		{
			match(LITERAL_select);
			{
			switch ( LA(1)) {
			case LITERAL_singleton:
			{
				AST tmp41_AST = null;
				tmp41_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp41_AST);
				match(LITERAL_singleton);
				break;
			}
			case IDENTIFIER:
			case OPEN_PAREN:
			case INTEGER:
			case QUOTED_STRING:
			case FLOAT:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_null:
			case LITERAL_count:
			case LITERAL_max:
			case LITERAL_min:
			case LITERAL_sum:
			case LITERAL_avg:
			case LITERAL_substr:
			case LITERAL_indexof:
			case LITERAL_lower:
			case LITERAL_upper:
			case LITERAL_stddev:
			case LITERAL_greatest:
			case LITERAL_least:
			case LITERAL_ceil:
			case LITERAL_floor:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			collection_select_list();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case LITERAL_from:
		case LITERAL_where:
		case CLOSE_PAREN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case LITERAL_from:
		{
			from_list();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case LITERAL_where:
		case CLOSE_PAREN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case LITERAL_where:
		{
			where_clause();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case CLOSE_PAREN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(CLOSE_PAREN);
		if ( inputState.guessing==0 ) {
			collection_path_expression_AST = (AST)currentAST.root;
			collection_path_expression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FIELD,"FIELD")).add(collection_path_expression_AST));
			currentAST.root = collection_path_expression_AST;
			currentAST.child = collection_path_expression_AST!=null &&collection_path_expression_AST.getFirstChild()!=null ?
				collection_path_expression_AST.getFirstChild() : collection_path_expression_AST;
			currentAST.advanceChildToEnd();
		}
		collection_path_expression_AST = (AST)currentAST.root;
		returnAST = collection_path_expression_AST;
	}

	public final void thing() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST thing_AST = null;

		{
		_loop544:
		do {
			if ((LA(1)==IDENTIFIER) && (LA(2)==DOT)) {
				AST tmp43_AST = null;
				tmp43_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp43_AST);
				match(IDENTIFIER);
				match(DOT);
			}
			else {
				break _loop544;
			}

		} while (true);
		}
		AST tmp45_AST = null;
		tmp45_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp45_AST);
		match(IDENTIFIER);
		if ( inputState.guessing==0 ) {
			thing_AST = (AST)currentAST.root;
			thing_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FIELD,"FIELD")).add(thing_AST));
			currentAST.root = thing_AST;
			currentAST.child = thing_AST!=null &&thing_AST.getFirstChild()!=null ?
				thing_AST.getFirstChild() : thing_AST;
			currentAST.advanceChildToEnd();
		}
		thing_AST = (AST)currentAST.root;
		returnAST = thing_AST;
	}

	public final void constant() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constant_AST = null;

		{
		switch ( LA(1)) {
		case QUOTED_STRING:
		{
			AST tmp46_AST = null;
			tmp46_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp46_AST);
			match(QUOTED_STRING);
			break;
		}
		case INTEGER:
		{
			AST tmp47_AST = null;
			tmp47_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp47_AST);
			match(INTEGER);
			break;
		}
		case FLOAT:
		{
			AST tmp48_AST = null;
			tmp48_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp48_AST);
			match(FLOAT);
			break;
		}
		case LITERAL_true:
		{
			AST tmp49_AST = null;
			tmp49_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp49_AST);
			match(LITERAL_true);
			break;
		}
		case LITERAL_false:
		{
			AST tmp50_AST = null;
			tmp50_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp50_AST);
			match(LITERAL_false);
			break;
		}
		case LITERAL_null:
		{
			AST tmp51_AST = null;
			tmp51_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp51_AST);
			match(LITERAL_null);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			constant_AST = (AST)currentAST.root;
			constant_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONSTANT,"CONSTANT")).add(constant_AST));
			currentAST.root = constant_AST;
			currentAST.child = constant_AST!=null &&constant_AST.getFirstChild()!=null ?
				constant_AST.getFirstChild() : constant_AST;
			currentAST.advanceChildToEnd();
		}
		constant_AST = (AST)currentAST.root;
		returnAST = constant_AST;
	}

	public final void safe_function() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST safe_function_AST = null;

		{
		switch ( LA(1)) {
		case LITERAL_count:
		{
			AST tmp52_AST = null;
			tmp52_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp52_AST);
			match(LITERAL_count);
			match(OPEN_PAREN);
			match(ASTERISK);
			match(CLOSE_PAREN);
			break;
		}
		case LITERAL_max:
		{
			AST tmp56_AST = null;
			tmp56_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp56_AST);
			match(LITERAL_max);
			match(OPEN_PAREN);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			break;
		}
		case LITERAL_min:
		{
			AST tmp59_AST = null;
			tmp59_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp59_AST);
			match(LITERAL_min);
			match(OPEN_PAREN);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			break;
		}
		case LITERAL_sum:
		{
			AST tmp62_AST = null;
			tmp62_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp62_AST);
			match(LITERAL_sum);
			match(OPEN_PAREN);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			break;
		}
		case LITERAL_avg:
		{
			AST tmp65_AST = null;
			tmp65_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp65_AST);
			match(LITERAL_avg);
			match(OPEN_PAREN);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			break;
		}
		case LITERAL_substr:
		{
			AST tmp68_AST = null;
			tmp68_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp68_AST);
			match(LITERAL_substr);
			match(OPEN_PAREN);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(COMMA);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case COMMA:
			{
				match(COMMA);
				abstract_value();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case CLOSE_PAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(CLOSE_PAREN);
			break;
		}
		case LITERAL_indexof:
		{
			AST tmp73_AST = null;
			tmp73_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp73_AST);
			match(LITERAL_indexof);
			match(OPEN_PAREN);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(COMMA);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			break;
		}
		case LITERAL_lower:
		{
			AST tmp77_AST = null;
			tmp77_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp77_AST);
			match(LITERAL_lower);
			match(OPEN_PAREN);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			break;
		}
		case LITERAL_upper:
		{
			AST tmp80_AST = null;
			tmp80_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp80_AST);
			match(LITERAL_upper);
			match(OPEN_PAREN);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			break;
		}
		case LITERAL_stddev:
		{
			AST tmp83_AST = null;
			tmp83_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp83_AST);
			match(LITERAL_stddev);
			match(OPEN_PAREN);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			break;
		}
		case LITERAL_greatest:
		{
			AST tmp86_AST = null;
			tmp86_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp86_AST);
			match(LITERAL_greatest);
			match(OPEN_PAREN);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(COMMA);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			break;
		}
		case LITERAL_least:
		{
			AST tmp90_AST = null;
			tmp90_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp90_AST);
			match(LITERAL_least);
			match(OPEN_PAREN);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(COMMA);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			break;
		}
		case LITERAL_ceil:
		{
			AST tmp94_AST = null;
			tmp94_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp94_AST);
			match(LITERAL_ceil);
			match(OPEN_PAREN);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			break;
		}
		case LITERAL_floor:
		{
			AST tmp97_AST = null;
			tmp97_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp97_AST);
			match(LITERAL_floor);
			match(OPEN_PAREN);
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			safe_function_AST = (AST)currentAST.root;
			safe_function_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SAFE_FUNCTION,"SAFE_FUNCTION")).add(safe_function_AST));
			currentAST.root = safe_function_AST;
			currentAST.child = safe_function_AST!=null &&safe_function_AST.getFirstChild()!=null ?
				safe_function_AST.getFirstChild() : safe_function_AST;
			currentAST.advanceChildToEnd();
		}
		safe_function_AST = (AST)currentAST.root;
		returnAST = safe_function_AST;
	}

	public final void paren_value() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST paren_value_AST = null;

		match(OPEN_PAREN);
		abstract_value();
		astFactory.addASTChild(currentAST, returnAST);
		match(CLOSE_PAREN);
		paren_value_AST = (AST)currentAST.root;
		returnAST = paren_value_AST;
	}

	public final void objectstorebag() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST objectstorebag_AST = null;

		match(LITERAL_bag);
		match(OPEN_PAREN);
		AST tmp104_AST = null;
		tmp104_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp104_AST);
		match(INTEGER);
		match(CLOSE_PAREN);
		if ( inputState.guessing==0 ) {
			objectstorebag_AST = (AST)currentAST.root;
			objectstorebag_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(OBJECTSTOREBAG,"OBJECTSTOREBAG")).add(objectstorebag_AST));
			currentAST.root = objectstorebag_AST;
			currentAST.child = objectstorebag_AST!=null &&objectstorebag_AST.getFirstChild()!=null ?
				objectstorebag_AST.getFirstChild() : objectstorebag_AST;
			currentAST.advanceChildToEnd();
		}
		objectstorebag_AST = (AST)currentAST.root;
		returnAST = objectstorebag_AST;
	}

	public final void bags_for() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST bags_for_AST = null;

		match(LITERAL_bags);
		match(LITERAL_for);
		AST tmp108_AST = null;
		tmp108_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp108_AST);
		match(INTEGER);
		{
		switch ( LA(1)) {
		case LITERAL_in:
		{
			match(LITERAL_in);
			match(LITERAL_bags);
			AST tmp111_AST = null;
			tmp111_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp111_AST);
			match(QUESTION_MARK);
			break;
		}
		case EOF:
		case COMMA:
		case LITERAL_from:
		case LITERAL_where:
		case LITERAL_group:
		case LITERAL_order:
		case CLOSE_PAREN:
		case LITERAL_limit:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			bags_for_AST = (AST)currentAST.root;
			bags_for_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BAGS_FOR,"BAGS_FOR")).add(bags_for_AST));
			currentAST.root = bags_for_AST;
			currentAST.child = bags_for_AST!=null &&bags_for_AST.getFirstChild()!=null ?
				bags_for_AST.getFirstChild() : bags_for_AST;
			currentAST.advanceChildToEnd();
		}
		bags_for_AST = (AST)currentAST.root;
		returnAST = bags_for_AST;
	}

	public final void table() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_AST = null;

		table_name();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case LITERAL_as:
		case IDENTIFIER:
		{
			{
			switch ( LA(1)) {
			case LITERAL_as:
			{
				match(LITERAL_as);
				break;
			}
			case IDENTIFIER:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			table_alias();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case EOF:
		case COMMA:
		case LITERAL_where:
		case LITERAL_group:
		case LITERAL_order:
		case CLOSE_PAREN:
		case LITERAL_limit:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			table_AST = (AST)currentAST.root;
			table_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TABLE,"TABLE")).add(table_AST));
			currentAST.root = table_AST;
			currentAST.child = table_AST!=null &&table_AST.getFirstChild()!=null ?
				table_AST.getFirstChild() : table_AST;
			currentAST.advanceChildToEnd();
		}
		table_AST = (AST)currentAST.root;
		returnAST = table_AST;
	}

	public final void subquery() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subquery_AST = null;

		match(OPEN_PAREN);
		iql_statement();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case LITERAL_limit:
		{
			subquery_limit();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case CLOSE_PAREN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(CLOSE_PAREN);
		{
		switch ( LA(1)) {
		case LITERAL_as:
		{
			match(LITERAL_as);
			break;
		}
		case IDENTIFIER:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		table_alias();
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			subquery_AST = (AST)currentAST.root;
			subquery_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SUBQUERY,"SUBQUERY")).add(subquery_AST));
			currentAST.root = subquery_AST;
			currentAST.child = subquery_AST!=null &&subquery_AST.getFirstChild()!=null ?
				subquery_AST.getFirstChild() : subquery_AST;
			currentAST.advanceChildToEnd();
		}
		subquery_AST = (AST)currentAST.root;
		returnAST = subquery_AST;
	}

	public final void multitable() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST multitable_AST = null;

		match(OPEN_PAREN);
		table_name();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop509:
		do {
			if ((LA(1)==COMMA)) {
				match(COMMA);
				table_name();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop509;
			}

		} while (true);
		}
		match(CLOSE_PAREN);
		{
		switch ( LA(1)) {
		case LITERAL_as:
		{
			match(LITERAL_as);
			break;
		}
		case IDENTIFIER:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		table_alias();
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			multitable_AST = (AST)currentAST.root;
			multitable_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TABLE,"TABLE")).add(multitable_AST));
			currentAST.root = multitable_AST;
			currentAST.child = multitable_AST!=null &&multitable_AST.getFirstChild()!=null ?
				multitable_AST.getFirstChild() : multitable_AST;
			currentAST.advanceChildToEnd();
		}
		multitable_AST = (AST)currentAST.root;
		returnAST = multitable_AST;
	}

	public final void query_class_bag() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST query_class_bag_AST = null;

		switch ( LA(1)) {
		case QUESTION_MARK:
		{
			AST tmp120_AST = null;
			tmp120_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp120_AST);
			match(QUESTION_MARK);
			match(COLONTYPE);
			table_name();
			astFactory.addASTChild(currentAST, returnAST);
			match(LITERAL_as);
			table_alias();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				query_class_bag_AST = (AST)currentAST.root;
				query_class_bag_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TABLE,"TABLE")).add(query_class_bag_AST));
				currentAST.root = query_class_bag_AST;
				currentAST.child = query_class_bag_AST!=null &&query_class_bag_AST.getFirstChild()!=null ?
					query_class_bag_AST.getFirstChild() : query_class_bag_AST;
				currentAST.advanceChildToEnd();
			}
			query_class_bag_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_bag:
		{
			objectstorebag();
			astFactory.addASTChild(currentAST, returnAST);
			match(COLONTYPE);
			table_name();
			astFactory.addASTChild(currentAST, returnAST);
			match(LITERAL_as);
			table_alias();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				query_class_bag_AST = (AST)currentAST.root;
				query_class_bag_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TABLE,"TABLE")).add(query_class_bag_AST));
				currentAST.root = query_class_bag_AST;
				currentAST.child = query_class_bag_AST!=null &&query_class_bag_AST.getFirstChild()!=null ?
					query_class_bag_AST.getFirstChild() : query_class_bag_AST;
				currentAST.advanceChildToEnd();
			}
			query_class_bag_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = query_class_bag_AST;
	}

	public final void query_class_bag_multi() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST query_class_bag_multi_AST = null;

		switch ( LA(1)) {
		case QUESTION_MARK:
		{
			AST tmp125_AST = null;
			tmp125_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp125_AST);
			match(QUESTION_MARK);
			match(COLONTYPE);
			match(OPEN_PAREN);
			table_name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop517:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					table_name();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop517;
				}

			} while (true);
			}
			match(CLOSE_PAREN);
			match(LITERAL_as);
			table_alias();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				query_class_bag_multi_AST = (AST)currentAST.root;
				query_class_bag_multi_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TABLE,"TABLE")).add(query_class_bag_multi_AST));
				currentAST.root = query_class_bag_multi_AST;
				currentAST.child = query_class_bag_multi_AST!=null &&query_class_bag_multi_AST.getFirstChild()!=null ?
					query_class_bag_multi_AST.getFirstChild() : query_class_bag_multi_AST;
				currentAST.advanceChildToEnd();
			}
			query_class_bag_multi_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_bag:
		{
			objectstorebag();
			astFactory.addASTChild(currentAST, returnAST);
			match(COLONTYPE);
			match(OPEN_PAREN);
			table_name();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop519:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					table_name();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop519;
				}

			} while (true);
			}
			match(CLOSE_PAREN);
			match(LITERAL_as);
			table_alias();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				query_class_bag_multi_AST = (AST)currentAST.root;
				query_class_bag_multi_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TABLE,"TABLE")).add(query_class_bag_multi_AST));
				currentAST.root = query_class_bag_multi_AST;
				currentAST.child = query_class_bag_multi_AST!=null &&query_class_bag_multi_AST.getFirstChild()!=null ?
					query_class_bag_multi_AST.getFirstChild() : query_class_bag_multi_AST;
				currentAST.advanceChildToEnd();
			}
			query_class_bag_multi_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = query_class_bag_multi_AST;
	}

	public final void safe_abstract_value() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST safe_abstract_value_AST = null;

		boolean synPredMatched495 = false;
		if (((_tokenSet_0.member(LA(1))) && (_tokenSet_7.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_49.member(LA(4))) && (_tokenSet_34.member(LA(5))) && (_tokenSet_35.member(LA(6))) && (_tokenSet_14.member(LA(7))))) {
			int _m495 = mark();
			synPredMatched495 = true;
			inputState.guessing++;
			try {
				{
				typecast();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched495 = false;
			}
			rewind(_m495);
inputState.guessing--;
		}
		if ( synPredMatched495 ) {
			typecast();
			astFactory.addASTChild(currentAST, returnAST);
			safe_abstract_value_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_17.member(LA(1))) && (_tokenSet_55.member(LA(2)))) {
			constant();
			astFactory.addASTChild(currentAST, returnAST);
			safe_abstract_value_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==IDENTIFIER) && (_tokenSet_56.member(LA(2))) && (_tokenSet_38.member(LA(3))) && (_tokenSet_35.member(LA(4))) && (_tokenSet_14.member(LA(5))) && (_tokenSet_15.member(LA(6))) && (_tokenSet_15.member(LA(7)))) {
			thing();
			astFactory.addASTChild(currentAST, returnAST);
			safe_abstract_value_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_18.member(LA(1))) && (LA(2)==OPEN_PAREN) && (_tokenSet_19.member(LA(3))) && (_tokenSet_4.member(LA(4))) && (_tokenSet_49.member(LA(5))) && (_tokenSet_34.member(LA(6))) && (_tokenSet_35.member(LA(7)))) {
			safe_function();
			astFactory.addASTChild(currentAST, returnAST);
			safe_abstract_value_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==OPEN_PAREN) && (_tokenSet_0.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_49.member(LA(4))) && (_tokenSet_34.member(LA(5))) && (_tokenSet_35.member(LA(6))) && (_tokenSet_14.member(LA(7)))) {
			paren_value();
			astFactory.addASTChild(currentAST, returnAST);
			safe_abstract_value_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = safe_abstract_value_AST;
	}

	public final void table_alias() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_alias_AST = null;

		AST tmp136_AST = null;
		tmp136_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp136_AST);
		match(IDENTIFIER);
		if ( inputState.guessing==0 ) {
			table_alias_AST = (AST)currentAST.root;
			table_alias_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TABLE_ALIAS,"TABLE_ALIAS")).add(table_alias_AST));
			currentAST.root = table_alias_AST;
			currentAST.child = table_alias_AST!=null &&table_alias_AST.getFirstChild()!=null ?
				table_alias_AST.getFirstChild() : table_alias_AST;
			currentAST.advanceChildToEnd();
		}
		table_alias_AST = (AST)currentAST.root;
		returnAST = table_alias_AST;
	}

	public final void table_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST table_name_AST = null;

		{
		_loop513:
		do {
			if ((LA(1)==IDENTIFIER) && (LA(2)==DOT)) {
				AST tmp137_AST = null;
				tmp137_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp137_AST);
				match(IDENTIFIER);
				match(DOT);
			}
			else {
				break _loop513;
			}

		} while (true);
		}
		AST tmp139_AST = null;
		tmp139_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp139_AST);
		match(IDENTIFIER);
		if ( inputState.guessing==0 ) {
			table_name_AST = (AST)currentAST.root;
			table_name_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TABLE_NAME,"TABLE_NAME")).add(table_name_AST));
			currentAST.root = table_name_AST;
			currentAST.child = table_name_AST!=null &&table_name_AST.getFirstChild()!=null ?
				table_name_AST.getFirstChild() : table_name_AST;
			currentAST.advanceChildToEnd();
		}
		table_name_AST = (AST)currentAST.root;
		returnAST = table_name_AST;
	}

	public final void subquery_limit() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subquery_limit_AST = null;

		match(LITERAL_limit);
		AST tmp141_AST = null;
		tmp141_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp141_AST);
		match(INTEGER);
		if ( inputState.guessing==0 ) {
			subquery_limit_AST = (AST)currentAST.root;
			subquery_limit_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SUBQUERY_LIMIT,"SUBQUERY_LIMIT")).add(subquery_limit_AST));
			currentAST.root = subquery_limit_AST;
			currentAST.child = subquery_limit_AST!=null &&subquery_limit_AST.getFirstChild()!=null ?
				subquery_limit_AST.getFirstChild() : subquery_limit_AST;
			currentAST.advanceChildToEnd();
		}
		subquery_limit_AST = (AST)currentAST.root;
		returnAST = subquery_limit_AST;
	}

	public final void collection_select_list() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST collection_select_list_AST = null;

		collection_select_value();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop533:
		do {
			if ((LA(1)==COMMA)) {
				match(COMMA);
				collection_select_value();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop533;
			}

		} while (true);
		}
		if ( inputState.guessing==0 ) {
			collection_select_list_AST = (AST)currentAST.root;
			collection_select_list_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(COLLECTION_SELECT_LIST,"COLLECTION_SELECT_LIST")).add(collection_select_list_AST));
			currentAST.root = collection_select_list_AST;
			currentAST.child = collection_select_list_AST!=null &&collection_select_list_AST.getFirstChild()!=null ?
				collection_select_list_AST.getFirstChild() : collection_select_list_AST;
			currentAST.advanceChildToEnd();
		}
		collection_select_list_AST = (AST)currentAST.root;
		returnAST = collection_select_list_AST;
	}

	public final void collection_select_value() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST collection_select_value_AST = null;

		{
		boolean synPredMatched537 = false;
		if (((_tokenSet_0.member(LA(1))) && (_tokenSet_1.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_57.member(LA(4))) && (_tokenSet_58.member(LA(5))) && (_tokenSet_59.member(LA(6))) && (_tokenSet_60.member(LA(7))))) {
			int _m537 = mark();
			synPredMatched537 = true;
			inputState.guessing++;
			try {
				{
				unsafe_function();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched537 = false;
			}
			rewind(_m537);
inputState.guessing--;
		}
		if ( synPredMatched537 ) {
			unsafe_function();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else {
			boolean synPredMatched539 = false;
			if (((_tokenSet_0.member(LA(1))) && (_tokenSet_7.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_57.member(LA(4))) && (_tokenSet_58.member(LA(5))) && (_tokenSet_59.member(LA(6))) && (_tokenSet_60.member(LA(7))))) {
				int _m539 = mark();
				synPredMatched539 = true;
				inputState.guessing++;
				try {
					{
					typecast();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched539 = false;
				}
				rewind(_m539);
inputState.guessing--;
			}
			if ( synPredMatched539 ) {
				typecast();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				boolean synPredMatched541 = false;
				if (((LA(1)==IDENTIFIER) && (LA(2)==DOT) && (LA(3)==IDENTIFIER) && (LA(4)==OPEN_PAREN) && (_tokenSet_8.member(LA(5))) && (_tokenSet_61.member(LA(6))) && (_tokenSet_59.member(LA(7))))) {
					int _m541 = mark();
					synPredMatched541 = true;
					inputState.guessing++;
					try {
						{
						collection_path_expression();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched541 = false;
					}
					rewind(_m541);
inputState.guessing--;
				}
				if ( synPredMatched541 ) {
					collection_path_expression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((LA(1)==IDENTIFIER) && (_tokenSet_62.member(LA(2))) && (_tokenSet_63.member(LA(3))) && (_tokenSet_59.member(LA(4))) && (_tokenSet_60.member(LA(5))) && (_tokenSet_29.member(LA(6))) && (_tokenSet_29.member(LA(7)))) {
					thing();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((_tokenSet_17.member(LA(1))) && (_tokenSet_64.member(LA(2)))) {
					constant();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((_tokenSet_18.member(LA(1))) && (LA(2)==OPEN_PAREN) && (_tokenSet_19.member(LA(3))) && (_tokenSet_4.member(LA(4))) && (_tokenSet_57.member(LA(5))) && (_tokenSet_58.member(LA(6))) && (_tokenSet_59.member(LA(7)))) {
					safe_function();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((LA(1)==OPEN_PAREN) && (_tokenSet_0.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_57.member(LA(4))) && (_tokenSet_58.member(LA(5))) && (_tokenSet_59.member(LA(6))) && (_tokenSet_60.member(LA(7)))) {
					paren_value();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}}
				}
				collection_select_value_AST = (AST)currentAST.root;
				returnAST = collection_select_value_AST;
			}

	public final void field_name() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST field_name_AST = null;

		AST tmp143_AST = null;
		tmp143_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp143_AST);
		match(IDENTIFIER);
		if ( inputState.guessing==0 ) {
			field_name_AST = (AST)currentAST.root;
			field_name_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FIELD_NAME,"FIELD_NAME")).add(field_name_AST));
			currentAST.root = field_name_AST;
			currentAST.child = field_name_AST!=null &&field_name_AST.getFirstChild()!=null ?
				field_name_AST.getFirstChild() : field_name_AST;
			currentAST.advanceChildToEnd();
		}
		field_name_AST = (AST)currentAST.root;
		returnAST = field_name_AST;
	}

	public final void constraint_set() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constraint_set_AST = null;

		boolean synPredMatched588 = false;
		if (((_tokenSet_25.member(LA(1))) && (_tokenSet_65.member(LA(2))) && (_tokenSet_27.member(LA(3))) && (_tokenSet_28.member(LA(4))) && (_tokenSet_29.member(LA(5))) && (_tokenSet_29.member(LA(6))) && (_tokenSet_15.member(LA(7))))) {
			int _m588 = mark();
			synPredMatched588 = true;
			inputState.guessing++;
			try {
				{
				or_constraint_set();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched588 = false;
			}
			rewind(_m588);
inputState.guessing--;
		}
		if ( synPredMatched588 ) {
			or_constraint_set();
			astFactory.addASTChild(currentAST, returnAST);
			constraint_set_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_25.member(LA(1))) && (_tokenSet_66.member(LA(2))) && (_tokenSet_27.member(LA(3))) && (_tokenSet_28.member(LA(4))) && (_tokenSet_29.member(LA(5))) && (_tokenSet_29.member(LA(6))) && (_tokenSet_15.member(LA(7)))) {
			and_constraint_set();
			astFactory.addASTChild(currentAST, returnAST);
			constraint_set_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = constraint_set_AST;
	}

	public final void safe_abstract_constraint() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST safe_abstract_constraint_AST = null;

		switch ( LA(1)) {
		case LITERAL_exists:
		case LITERAL_does:
		{
			subquery_exists_constraint();
			astFactory.addASTChild(currentAST, returnAST);
			safe_abstract_constraint_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_not:
		{
			not_constraint();
			astFactory.addASTChild(currentAST, returnAST);
			safe_abstract_constraint_AST = (AST)currentAST.root;
			break;
		}
		default:
			boolean synPredMatched568 = false;
			if (((LA(1)==OPEN_PAREN) && (_tokenSet_25.member(LA(2))) && (_tokenSet_67.member(LA(3))) && (_tokenSet_68.member(LA(4))) && (_tokenSet_32.member(LA(5))) && (_tokenSet_29.member(LA(6))) && (_tokenSet_15.member(LA(7))))) {
				int _m568 = mark();
				synPredMatched568 = true;
				inputState.guessing++;
				try {
					{
					paren_constraint();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched568 = false;
				}
				rewind(_m568);
inputState.guessing--;
			}
			if ( synPredMatched568 ) {
				paren_constraint();
				astFactory.addASTChild(currentAST, returnAST);
				safe_abstract_constraint_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched570 = false;
				if (((_tokenSet_0.member(LA(1))) && (_tokenSet_69.member(LA(2))) && (_tokenSet_70.member(LA(3))) && (_tokenSet_71.member(LA(4))) && (_tokenSet_72.member(LA(5))) && (_tokenSet_46.member(LA(6))) && (_tokenSet_44.member(LA(7))))) {
					int _m570 = mark();
					synPredMatched570 = true;
					inputState.guessing++;
					try {
						{
						bag_constraint();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched570 = false;
					}
					rewind(_m570);
inputState.guessing--;
				}
				if ( synPredMatched570 ) {
					bag_constraint();
					astFactory.addASTChild(currentAST, returnAST);
					safe_abstract_constraint_AST = (AST)currentAST.root;
				}
				else {
					boolean synPredMatched572 = false;
					if (((_tokenSet_0.member(LA(1))) && (_tokenSet_69.member(LA(2))) && (_tokenSet_73.member(LA(3))) && (_tokenSet_74.member(LA(4))) && (_tokenSet_75.member(LA(5))) && (_tokenSet_76.member(LA(6))) && (_tokenSet_77.member(LA(7))))) {
						int _m572 = mark();
						synPredMatched572 = true;
						inputState.guessing++;
						try {
							{
							subquery_constraint();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched572 = false;
						}
						rewind(_m572);
inputState.guessing--;
					}
					if ( synPredMatched572 ) {
						subquery_constraint();
						astFactory.addASTChild(currentAST, returnAST);
						safe_abstract_constraint_AST = (AST)currentAST.root;
					}
					else {
						boolean synPredMatched574 = false;
						if (((LA(1)==IDENTIFIER||LA(1)==QUESTION_MARK) && (LA(2)==DOT||LA(2)==LITERAL_does||LA(2)==LITERAL_contains) && (LA(3)==IDENTIFIER||LA(3)==QUESTION_MARK||LA(3)==LITERAL_not) && (_tokenSet_78.member(LA(4))) && (_tokenSet_79.member(LA(5))) && (_tokenSet_80.member(LA(6))) && (_tokenSet_44.member(LA(7))))) {
							int _m574 = mark();
							synPredMatched574 = true;
							inputState.guessing++;
							try {
								{
								contains_constraint();
								}
							}
							catch (RecognitionException pe) {
								synPredMatched574 = false;
							}
							rewind(_m574);
inputState.guessing--;
						}
						if ( synPredMatched574 ) {
							contains_constraint();
							astFactory.addASTChild(currentAST, returnAST);
							safe_abstract_constraint_AST = (AST)currentAST.root;
						}
						else if ((LA(1)==LITERAL_true) && (_tokenSet_81.member(LA(2)))) {
							AST tmp144_AST = null;
							tmp144_AST = astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp144_AST);
							match(LITERAL_true);
							safe_abstract_constraint_AST = (AST)currentAST.root;
						}
						else if ((LA(1)==LITERAL_false) && (_tokenSet_81.member(LA(2)))) {
							AST tmp145_AST = null;
							tmp145_AST = astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp145_AST);
							match(LITERAL_false);
							safe_abstract_constraint_AST = (AST)currentAST.root;
						}
						else if ((_tokenSet_0.member(LA(1))) && (_tokenSet_82.member(LA(2))) && (_tokenSet_83.member(LA(3))) && (_tokenSet_84.member(LA(4))) && (_tokenSet_46.member(LA(5))) && (_tokenSet_44.member(LA(6))) && (_tokenSet_47.member(LA(7)))) {
							constraint();
							astFactory.addASTChild(currentAST, returnAST);
							safe_abstract_constraint_AST = (AST)currentAST.root;
						}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					}}}}
					returnAST = safe_abstract_constraint_AST;
				}

	public final void paren_constraint() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST paren_constraint_AST = null;

		match(OPEN_PAREN);
		abstract_constraint();
		astFactory.addASTChild(currentAST, returnAST);
		match(CLOSE_PAREN);
		paren_constraint_AST = (AST)currentAST.root;
		returnAST = paren_constraint_AST;
	}

	public final void bag_constraint() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST bag_constraint_AST = null;

		boolean synPredMatched601 = false;
		if (((_tokenSet_0.member(LA(1))) && (_tokenSet_85.member(LA(2))) && (_tokenSet_86.member(LA(3))) && (_tokenSet_87.member(LA(4))) && (_tokenSet_88.member(LA(5))) && (_tokenSet_88.member(LA(6))) && (_tokenSet_89.member(LA(7))))) {
			int _m601 = mark();
			synPredMatched601 = true;
			inputState.guessing++;
			try {
				{
				abstract_value();
				match(LITERAL_in);
				objectstorebag();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched601 = false;
			}
			rewind(_m601);
inputState.guessing--;
		}
		if ( synPredMatched601 ) {
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(LITERAL_in);
			objectstorebag();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				bag_constraint_AST = (AST)currentAST.root;
				bag_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BAG_CONSTRAINT,"BAG_CONSTRAINT")).add(bag_constraint_AST));
				currentAST.root = bag_constraint_AST;
				currentAST.child = bag_constraint_AST!=null &&bag_constraint_AST.getFirstChild()!=null ?
					bag_constraint_AST.getFirstChild() : bag_constraint_AST;
				currentAST.advanceChildToEnd();
			}
			bag_constraint_AST = (AST)currentAST.root;
		}
		else {
			boolean synPredMatched603 = false;
			if (((_tokenSet_0.member(LA(1))) && (_tokenSet_90.member(LA(2))) && (_tokenSet_73.member(LA(3))) && (_tokenSet_91.member(LA(4))) && (_tokenSet_92.member(LA(5))) && (_tokenSet_93.member(LA(6))) && (_tokenSet_93.member(LA(7))))) {
				int _m603 = mark();
				synPredMatched603 = true;
				inputState.guessing++;
				try {
					{
					abstract_value();
					match(LITERAL_not);
					match(LITERAL_in);
					objectstorebag();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched603 = false;
				}
				rewind(_m603);
inputState.guessing--;
			}
			if ( synPredMatched603 ) {
				abstract_value();
				astFactory.addASTChild(currentAST, returnAST);
				match(LITERAL_not);
				match(LITERAL_in);
				objectstorebag();
				astFactory.addASTChild(currentAST, returnAST);
				if ( inputState.guessing==0 ) {
					bag_constraint_AST = (AST)currentAST.root;
					bag_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NOT_CONSTRAINT,"NOT_CONSTRAINT")).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BAG_CONSTRAINT,"BAG_CONSTRAINT")).add(bag_constraint_AST))));
					currentAST.root = bag_constraint_AST;
					currentAST.child = bag_constraint_AST!=null &&bag_constraint_AST.getFirstChild()!=null ?
						bag_constraint_AST.getFirstChild() : bag_constraint_AST;
					currentAST.advanceChildToEnd();
				}
				bag_constraint_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched605 = false;
				if (((_tokenSet_0.member(LA(1))) && (_tokenSet_85.member(LA(2))) && (_tokenSet_94.member(LA(3))) && (_tokenSet_95.member(LA(4))) && (_tokenSet_96.member(LA(5))) && (_tokenSet_46.member(LA(6))) && (_tokenSet_44.member(LA(7))))) {
					int _m605 = mark();
					synPredMatched605 = true;
					inputState.guessing++;
					try {
						{
						abstract_value();
						match(LITERAL_in);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched605 = false;
					}
					rewind(_m605);
inputState.guessing--;
				}
				if ( synPredMatched605 ) {
					abstract_value();
					astFactory.addASTChild(currentAST, returnAST);
					match(LITERAL_in);
					match(QUESTION_MARK);
					if ( inputState.guessing==0 ) {
						bag_constraint_AST = (AST)currentAST.root;
						bag_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BAG_CONSTRAINT,"BAG_CONSTRAINT")).add(bag_constraint_AST));
						currentAST.root = bag_constraint_AST;
						currentAST.child = bag_constraint_AST!=null &&bag_constraint_AST.getFirstChild()!=null ?
							bag_constraint_AST.getFirstChild() : bag_constraint_AST;
						currentAST.advanceChildToEnd();
					}
					bag_constraint_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_0.member(LA(1))) && (_tokenSet_90.member(LA(2))) && (_tokenSet_73.member(LA(3))) && (_tokenSet_97.member(LA(4))) && (_tokenSet_98.member(LA(5))) && (_tokenSet_96.member(LA(6))) && (_tokenSet_46.member(LA(7)))) {
					abstract_value();
					astFactory.addASTChild(currentAST, returnAST);
					match(LITERAL_not);
					match(LITERAL_in);
					match(QUESTION_MARK);
					if ( inputState.guessing==0 ) {
						bag_constraint_AST = (AST)currentAST.root;
						bag_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NOT_CONSTRAINT,"NOT_CONSTRAINT")).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BAG_CONSTRAINT,"BAG_CONSTRAINT")).add(bag_constraint_AST))));
						currentAST.root = bag_constraint_AST;
						currentAST.child = bag_constraint_AST!=null &&bag_constraint_AST.getFirstChild()!=null ?
							bag_constraint_AST.getFirstChild() : bag_constraint_AST;
						currentAST.advanceChildToEnd();
					}
					bag_constraint_AST = (AST)currentAST.root;
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}}
				returnAST = bag_constraint_AST;
			}

	public final void subquery_constraint() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subquery_constraint_AST = null;

		boolean synPredMatched597 = false;
		if (((_tokenSet_0.member(LA(1))) && (_tokenSet_85.member(LA(2))) && (_tokenSet_2.member(LA(3))) && (_tokenSet_99.member(LA(4))) && (_tokenSet_100.member(LA(5))) && (_tokenSet_101.member(LA(6))) && (_tokenSet_77.member(LA(7))))) {
			int _m597 = mark();
			synPredMatched597 = true;
			inputState.guessing++;
			try {
				{
				abstract_value();
				match(LITERAL_in);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched597 = false;
			}
			rewind(_m597);
inputState.guessing--;
		}
		if ( synPredMatched597 ) {
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(LITERAL_in);
			match(OPEN_PAREN);
			iql_statement();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			if ( inputState.guessing==0 ) {
				subquery_constraint_AST = (AST)currentAST.root;
				subquery_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SUBQUERY_CONSTRAINT,"SUBQUERY_CONSTRAINT")).add(subquery_constraint_AST));
				currentAST.root = subquery_constraint_AST;
				currentAST.child = subquery_constraint_AST!=null &&subquery_constraint_AST.getFirstChild()!=null ?
					subquery_constraint_AST.getFirstChild() : subquery_constraint_AST;
				currentAST.advanceChildToEnd();
			}
			subquery_constraint_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_0.member(LA(1))) && (_tokenSet_90.member(LA(2))) && (_tokenSet_73.member(LA(3))) && (_tokenSet_102.member(LA(4))) && (_tokenSet_74.member(LA(5))) && (_tokenSet_103.member(LA(6))) && (_tokenSet_76.member(LA(7)))) {
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			match(LITERAL_not);
			match(LITERAL_in);
			match(OPEN_PAREN);
			iql_statement();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			if ( inputState.guessing==0 ) {
				subquery_constraint_AST = (AST)currentAST.root;
				subquery_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NOT_CONSTRAINT,"NOT_CONSTRAINT")).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SUBQUERY_CONSTRAINT,"SUBQUERY_CONSTRAINT")).add(subquery_constraint_AST))));
				currentAST.root = subquery_constraint_AST;
				currentAST.child = subquery_constraint_AST!=null &&subquery_constraint_AST.getFirstChild()!=null ?
					subquery_constraint_AST.getFirstChild() : subquery_constraint_AST;
				currentAST.advanceChildToEnd();
			}
			subquery_constraint_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}

		returnAST = subquery_constraint_AST;
	}

	public final void contains_constraint() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST contains_constraint_AST = null;

		boolean synPredMatched608 = false;
		if (((LA(1)==QUESTION_MARK) && (LA(2)==DOT) && (LA(3)==IDENTIFIER) && (LA(4)==LITERAL_contains) && (LA(5)==QUESTION_MARK))) {
			int _m608 = mark();
			synPredMatched608 = true;
			inputState.guessing++;
			try {
				{
				collection_from_question_mark();
				match(LITERAL_contains);
				match(QUESTION_MARK);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched608 = false;
			}
			rewind(_m608);
inputState.guessing--;
		}
		if ( synPredMatched608 ) {
			collection_from_question_mark();
			astFactory.addASTChild(currentAST, returnAST);
			match(LITERAL_contains);
			AST tmp164_AST = null;
			tmp164_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp164_AST);
			match(QUESTION_MARK);
			if ( inputState.guessing==0 ) {
				contains_constraint_AST = (AST)currentAST.root;
				contains_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONTAINS_CONSTRAINT,"CONTAINS_CONSTRAINT")).add(contains_constraint_AST));
				currentAST.root = contains_constraint_AST;
				currentAST.child = contains_constraint_AST!=null &&contains_constraint_AST.getFirstChild()!=null ?
					contains_constraint_AST.getFirstChild() : contains_constraint_AST;
				currentAST.advanceChildToEnd();
			}
			contains_constraint_AST = (AST)currentAST.root;
		}
		else {
			boolean synPredMatched610 = false;
			if (((LA(1)==QUESTION_MARK) && (LA(2)==DOT) && (LA(3)==IDENTIFIER) && (LA(4)==LITERAL_does) && (LA(5)==LITERAL_not) && (LA(6)==LITERAL_contain) && (LA(7)==QUESTION_MARK))) {
				int _m610 = mark();
				synPredMatched610 = true;
				inputState.guessing++;
				try {
					{
					collection_from_question_mark();
					match(LITERAL_does);
					match(LITERAL_not);
					match(LITERAL_contain);
					match(QUESTION_MARK);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched610 = false;
				}
				rewind(_m610);
inputState.guessing--;
			}
			if ( synPredMatched610 ) {
				collection_from_question_mark();
				astFactory.addASTChild(currentAST, returnAST);
				match(LITERAL_does);
				match(LITERAL_not);
				match(LITERAL_contain);
				AST tmp168_AST = null;
				tmp168_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp168_AST);
				match(QUESTION_MARK);
				if ( inputState.guessing==0 ) {
					contains_constraint_AST = (AST)currentAST.root;
					contains_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NOT_CONSTRAINT,"NOT_CONSTRAINT")).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONTAINS_CONSTRAINT,"CONTAINS_CONSTRAINT")).add(contains_constraint_AST))));
					currentAST.root = contains_constraint_AST;
					currentAST.child = contains_constraint_AST!=null &&contains_constraint_AST.getFirstChild()!=null ?
						contains_constraint_AST.getFirstChild() : contains_constraint_AST;
					currentAST.advanceChildToEnd();
				}
				contains_constraint_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched612 = false;
				if (((LA(1)==QUESTION_MARK) && (LA(2)==DOT) && (LA(3)==IDENTIFIER) && (LA(4)==LITERAL_contains) && (LA(5)==IDENTIFIER))) {
					int _m612 = mark();
					synPredMatched612 = true;
					inputState.guessing++;
					try {
						{
						collection_from_question_mark();
						match(LITERAL_contains);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched612 = false;
					}
					rewind(_m612);
inputState.guessing--;
				}
				if ( synPredMatched612 ) {
					collection_from_question_mark();
					astFactory.addASTChild(currentAST, returnAST);
					match(LITERAL_contains);
					thing();
					astFactory.addASTChild(currentAST, returnAST);
					if ( inputState.guessing==0 ) {
						contains_constraint_AST = (AST)currentAST.root;
						contains_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONTAINS_CONSTRAINT,"CONTAINS_CONSTRAINT")).add(contains_constraint_AST));
						currentAST.root = contains_constraint_AST;
						currentAST.child = contains_constraint_AST!=null &&contains_constraint_AST.getFirstChild()!=null ?
							contains_constraint_AST.getFirstChild() : contains_constraint_AST;
						currentAST.advanceChildToEnd();
					}
					contains_constraint_AST = (AST)currentAST.root;
				}
				else {
					boolean synPredMatched614 = false;
					if (((LA(1)==QUESTION_MARK) && (LA(2)==DOT) && (LA(3)==IDENTIFIER) && (LA(4)==LITERAL_does) && (LA(5)==LITERAL_not) && (LA(6)==LITERAL_contain) && (LA(7)==IDENTIFIER))) {
						int _m614 = mark();
						synPredMatched614 = true;
						inputState.guessing++;
						try {
							{
							collection_from_question_mark();
							match(LITERAL_does);
							match(LITERAL_not);
							match(LITERAL_contain);
							}
						}
						catch (RecognitionException pe) {
							synPredMatched614 = false;
						}
						rewind(_m614);
inputState.guessing--;
					}
					if ( synPredMatched614 ) {
						collection_from_question_mark();
						astFactory.addASTChild(currentAST, returnAST);
						match(LITERAL_does);
						match(LITERAL_not);
						match(LITERAL_contain);
						thing();
						astFactory.addASTChild(currentAST, returnAST);
						if ( inputState.guessing==0 ) {
							contains_constraint_AST = (AST)currentAST.root;
							contains_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NOT_CONSTRAINT,"NOT_CONSTRAINT")).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONTAINS_CONSTRAINT,"CONTAINS_CONSTRAINT")).add(contains_constraint_AST))));
							currentAST.root = contains_constraint_AST;
							currentAST.child = contains_constraint_AST!=null &&contains_constraint_AST.getFirstChild()!=null ?
								contains_constraint_AST.getFirstChild() : contains_constraint_AST;
							currentAST.advanceChildToEnd();
						}
						contains_constraint_AST = (AST)currentAST.root;
					}
					else {
						boolean synPredMatched616 = false;
						if (((LA(1)==IDENTIFIER) && (LA(2)==DOT||LA(2)==LITERAL_contains) && (LA(3)==IDENTIFIER||LA(3)==QUESTION_MARK) && (_tokenSet_104.member(LA(4))) && (_tokenSet_79.member(LA(5))) && (_tokenSet_46.member(LA(6))) && (_tokenSet_44.member(LA(7))))) {
							int _m616 = mark();
							synPredMatched616 = true;
							inputState.guessing++;
							try {
								{
								thing();
								match(LITERAL_contains);
								match(QUESTION_MARK);
								}
							}
							catch (RecognitionException pe) {
								synPredMatched616 = false;
							}
							rewind(_m616);
inputState.guessing--;
						}
						if ( synPredMatched616 ) {
							thing();
							astFactory.addASTChild(currentAST, returnAST);
							match(LITERAL_contains);
							AST tmp174_AST = null;
							tmp174_AST = astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp174_AST);
							match(QUESTION_MARK);
							if ( inputState.guessing==0 ) {
								contains_constraint_AST = (AST)currentAST.root;
								contains_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONTAINS_CONSTRAINT,"CONTAINS_CONSTRAINT")).add(contains_constraint_AST));
								currentAST.root = contains_constraint_AST;
								currentAST.child = contains_constraint_AST!=null &&contains_constraint_AST.getFirstChild()!=null ?
									contains_constraint_AST.getFirstChild() : contains_constraint_AST;
								currentAST.advanceChildToEnd();
							}
							contains_constraint_AST = (AST)currentAST.root;
						}
						else {
							boolean synPredMatched618 = false;
							if (((LA(1)==IDENTIFIER) && (LA(2)==DOT||LA(2)==LITERAL_contains) && (LA(3)==IDENTIFIER) && (_tokenSet_104.member(LA(4))) && (_tokenSet_79.member(LA(5))) && (_tokenSet_46.member(LA(6))) && (_tokenSet_44.member(LA(7))))) {
								int _m618 = mark();
								synPredMatched618 = true;
								inputState.guessing++;
								try {
									{
									thing();
									match(LITERAL_contains);
									}
								}
								catch (RecognitionException pe) {
									synPredMatched618 = false;
								}
								rewind(_m618);
inputState.guessing--;
							}
							if ( synPredMatched618 ) {
								thing();
								astFactory.addASTChild(currentAST, returnAST);
								match(LITERAL_contains);
								thing();
								astFactory.addASTChild(currentAST, returnAST);
								if ( inputState.guessing==0 ) {
									contains_constraint_AST = (AST)currentAST.root;
									contains_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONTAINS_CONSTRAINT,"CONTAINS_CONSTRAINT")).add(contains_constraint_AST));
									currentAST.root = contains_constraint_AST;
									currentAST.child = contains_constraint_AST!=null &&contains_constraint_AST.getFirstChild()!=null ?
										contains_constraint_AST.getFirstChild() : contains_constraint_AST;
									currentAST.advanceChildToEnd();
								}
								contains_constraint_AST = (AST)currentAST.root;
							}
							else {
								boolean synPredMatched620 = false;
								if (((LA(1)==IDENTIFIER) && (LA(2)==DOT||LA(2)==LITERAL_does) && (LA(3)==IDENTIFIER||LA(3)==LITERAL_not) && (LA(4)==DOT||LA(4)==LITERAL_does||LA(4)==LITERAL_contain) && (LA(5)==IDENTIFIER||LA(5)==QUESTION_MARK||LA(5)==LITERAL_not) && (_tokenSet_105.member(LA(6))) && (_tokenSet_79.member(LA(7))))) {
									int _m620 = mark();
									synPredMatched620 = true;
									inputState.guessing++;
									try {
										{
										thing();
										match(LITERAL_does);
										match(LITERAL_not);
										match(LITERAL_contain);
										match(QUESTION_MARK);
										}
									}
									catch (RecognitionException pe) {
										synPredMatched620 = false;
									}
									rewind(_m620);
inputState.guessing--;
								}
								if ( synPredMatched620 ) {
									thing();
									astFactory.addASTChild(currentAST, returnAST);
									match(LITERAL_does);
									match(LITERAL_not);
									match(LITERAL_contain);
									AST tmp179_AST = null;
									tmp179_AST = astFactory.create(LT(1));
									astFactory.addASTChild(currentAST, tmp179_AST);
									match(QUESTION_MARK);
									if ( inputState.guessing==0 ) {
										contains_constraint_AST = (AST)currentAST.root;
										contains_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NOT_CONSTRAINT,"NOT_CONSTRAINT")).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONTAINS_CONSTRAINT,"CONTAINS_CONSTRAINT")).add(contains_constraint_AST))));
										currentAST.root = contains_constraint_AST;
										currentAST.child = contains_constraint_AST!=null &&contains_constraint_AST.getFirstChild()!=null ?
											contains_constraint_AST.getFirstChild() : contains_constraint_AST;
										currentAST.advanceChildToEnd();
									}
									contains_constraint_AST = (AST)currentAST.root;
								}
								else if ((LA(1)==IDENTIFIER) && (LA(2)==DOT||LA(2)==LITERAL_does) && (LA(3)==IDENTIFIER||LA(3)==LITERAL_not) && (LA(4)==DOT||LA(4)==LITERAL_does||LA(4)==LITERAL_contain) && (LA(5)==IDENTIFIER||LA(5)==LITERAL_not) && (_tokenSet_105.member(LA(6))) && (_tokenSet_79.member(LA(7)))) {
									thing();
									astFactory.addASTChild(currentAST, returnAST);
									match(LITERAL_does);
									match(LITERAL_not);
									match(LITERAL_contain);
									thing();
									astFactory.addASTChild(currentAST, returnAST);
									if ( inputState.guessing==0 ) {
										contains_constraint_AST = (AST)currentAST.root;
										contains_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NOT_CONSTRAINT,"NOT_CONSTRAINT")).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONTAINS_CONSTRAINT,"CONTAINS_CONSTRAINT")).add(contains_constraint_AST))));
										currentAST.root = contains_constraint_AST;
										currentAST.child = contains_constraint_AST!=null &&contains_constraint_AST.getFirstChild()!=null ?
											contains_constraint_AST.getFirstChild() : contains_constraint_AST;
										currentAST.advanceChildToEnd();
									}
									contains_constraint_AST = (AST)currentAST.root;
								}
								else {
									throw new NoViableAltException(LT(1), getFilename());
								}
								}}}}}}
								returnAST = contains_constraint_AST;
							}

	public final void subquery_exists_constraint() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subquery_exists_constraint_AST = null;

		switch ( LA(1)) {
		case LITERAL_exists:
		{
			match(LITERAL_exists);
			match(OPEN_PAREN);
			iql_statement();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			if ( inputState.guessing==0 ) {
				subquery_exists_constraint_AST = (AST)currentAST.root;
				subquery_exists_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SUBQUERY_EXISTS_CONSTRAINT,"SUBQUERY_EXISTS_CONSTRAINT")).add(subquery_exists_constraint_AST));
				currentAST.root = subquery_exists_constraint_AST;
				currentAST.child = subquery_exists_constraint_AST!=null &&subquery_exists_constraint_AST.getFirstChild()!=null ?
					subquery_exists_constraint_AST.getFirstChild() : subquery_exists_constraint_AST;
				currentAST.advanceChildToEnd();
			}
			subquery_exists_constraint_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_does:
		{
			match(LITERAL_does);
			match(LITERAL_not);
			match(LITERAL_exist);
			match(OPEN_PAREN);
			iql_statement();
			astFactory.addASTChild(currentAST, returnAST);
			match(CLOSE_PAREN);
			if ( inputState.guessing==0 ) {
				subquery_exists_constraint_AST = (AST)currentAST.root;
				subquery_exists_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NOT_CONSTRAINT,"NOT_CONSTRAINT")).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(SUBQUERY_EXISTS_CONSTRAINT,"SUBQUERY_EXISTS_CONSTRAINT")).add(subquery_exists_constraint_AST))));
				currentAST.root = subquery_exists_constraint_AST;
				currentAST.child = subquery_exists_constraint_AST!=null &&subquery_exists_constraint_AST.getFirstChild()!=null ?
					subquery_exists_constraint_AST.getFirstChild() : subquery_exists_constraint_AST;
				currentAST.advanceChildToEnd();
			}
			subquery_exists_constraint_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = subquery_exists_constraint_AST;
	}

	public final void constraint() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constraint_AST = null;

		boolean synPredMatched577 = false;
		if (((_tokenSet_0.member(LA(1))) && (_tokenSet_106.member(LA(2))) && (_tokenSet_107.member(LA(3))) && (_tokenSet_108.member(LA(4))) && (_tokenSet_46.member(LA(5))) && (_tokenSet_44.member(LA(6))) && (_tokenSet_47.member(LA(7))))) {
			int _m577 = mark();
			synPredMatched577 = true;
			inputState.guessing++;
			try {
				{
				abstract_value();
				match(ISNULL);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched577 = false;
			}
			rewind(_m577);
inputState.guessing--;
		}
		if ( synPredMatched577 ) {
			abstract_value();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp191_AST = null;
			tmp191_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp191_AST);
			match(ISNULL);
			if ( inputState.guessing==0 ) {
				constraint_AST = (AST)currentAST.root;
				constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONSTRAINT,"CONSTRAINT")).add(constraint_AST));
				currentAST.root = constraint_AST;
				currentAST.child = constraint_AST!=null &&constraint_AST.getFirstChild()!=null ?
					constraint_AST.getFirstChild() : constraint_AST;
				currentAST.advanceChildToEnd();
			}
			constraint_AST = (AST)currentAST.root;
		}
		else {
			boolean synPredMatched579 = false;
			if (((_tokenSet_0.member(LA(1))) && (_tokenSet_109.member(LA(2))) && (_tokenSet_107.member(LA(3))) && (_tokenSet_110.member(LA(4))) && (_tokenSet_46.member(LA(5))) && (_tokenSet_44.member(LA(6))) && (_tokenSet_47.member(LA(7))))) {
				int _m579 = mark();
				synPredMatched579 = true;
				inputState.guessing++;
				try {
					{
					abstract_value();
					match(ISNOTNULL);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched579 = false;
				}
				rewind(_m579);
inputState.guessing--;
			}
			if ( synPredMatched579 ) {
				abstract_value();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp192_AST = null;
				tmp192_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp192_AST);
				match(ISNOTNULL);
				if ( inputState.guessing==0 ) {
					constraint_AST = (AST)currentAST.root;
					constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONSTRAINT,"CONSTRAINT")).add(constraint_AST));
					currentAST.root = constraint_AST;
					currentAST.child = constraint_AST!=null &&constraint_AST.getFirstChild()!=null ?
						constraint_AST.getFirstChild() : constraint_AST;
					currentAST.advanceChildToEnd();
				}
				constraint_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched582 = false;
				if (((LA(1)==IDENTIFIER) && (LA(2)==DOT||LA(2)==EQ||LA(2)==NOT_EQ) && (LA(3)==IDENTIFIER||LA(3)==QUESTION_MARK) && (_tokenSet_111.member(LA(4))) && (_tokenSet_79.member(LA(5))) && (_tokenSet_46.member(LA(6))) && (_tokenSet_44.member(LA(7))))) {
					int _m582 = mark();
					synPredMatched582 = true;
					inputState.guessing++;
					try {
						{
						thing();
						{
						switch ( LA(1)) {
						case EQ:
						{
							match(EQ);
							break;
						}
						case NOT_EQ:
						{
							match(NOT_EQ);
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						match(QUESTION_MARK);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched582 = false;
					}
					rewind(_m582);
inputState.guessing--;
				}
				if ( synPredMatched582 ) {
					thing();
					astFactory.addASTChild(currentAST, returnAST);
					{
					switch ( LA(1)) {
					case EQ:
					{
						AST tmp193_AST = null;
						tmp193_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp193_AST);
						match(EQ);
						break;
					}
					case NOT_EQ:
					{
						AST tmp194_AST = null;
						tmp194_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp194_AST);
						match(NOT_EQ);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					AST tmp195_AST = null;
					tmp195_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp195_AST);
					match(QUESTION_MARK);
					if ( inputState.guessing==0 ) {
						constraint_AST = (AST)currentAST.root;
						constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONSTRAINT,"CONSTRAINT")).add(constraint_AST));
						currentAST.root = constraint_AST;
						currentAST.child = constraint_AST!=null &&constraint_AST.getFirstChild()!=null ?
							constraint_AST.getFirstChild() : constraint_AST;
						currentAST.advanceChildToEnd();
					}
					constraint_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_0.member(LA(1))) && (_tokenSet_112.member(LA(2))) && (_tokenSet_113.member(LA(3))) && (_tokenSet_114.member(LA(4))) && (_tokenSet_115.member(LA(5))) && (_tokenSet_46.member(LA(6))) && (_tokenSet_44.member(LA(7)))) {
					abstract_value();
					astFactory.addASTChild(currentAST, returnAST);
					comparison_op();
					astFactory.addASTChild(currentAST, returnAST);
					abstract_value();
					astFactory.addASTChild(currentAST, returnAST);
					if ( inputState.guessing==0 ) {
						constraint_AST = (AST)currentAST.root;
						constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONSTRAINT,"CONSTRAINT")).add(constraint_AST));
						currentAST.root = constraint_AST;
						currentAST.child = constraint_AST!=null &&constraint_AST.getFirstChild()!=null ?
							constraint_AST.getFirstChild() : constraint_AST;
						currentAST.advanceChildToEnd();
					}
					constraint_AST = (AST)currentAST.root;
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}}
				returnAST = constraint_AST;
			}

	public final void not_constraint() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST not_constraint_AST = null;

		match(LITERAL_not);
		safe_abstract_constraint();
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			not_constraint_AST = (AST)currentAST.root;
			not_constraint_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(NOT_CONSTRAINT,"NOT_CONSTRAINT")).add(not_constraint_AST));
			currentAST.root = not_constraint_AST;
			currentAST.child = not_constraint_AST!=null &&not_constraint_AST.getFirstChild()!=null ?
				not_constraint_AST.getFirstChild() : not_constraint_AST;
			currentAST.advanceChildToEnd();
		}
		not_constraint_AST = (AST)currentAST.root;
		returnAST = not_constraint_AST;
	}

	public final void comparison_op() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST comparison_op_AST = null;

		switch ( LA(1)) {
		case EQ:
		{
			AST tmp197_AST = null;
			tmp197_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp197_AST);
			match(EQ);
			comparison_op_AST = (AST)currentAST.root;
			break;
		}
		case LT:
		{
			AST tmp198_AST = null;
			tmp198_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp198_AST);
			match(LT);
			comparison_op_AST = (AST)currentAST.root;
			break;
		}
		case GT:
		{
			AST tmp199_AST = null;
			tmp199_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp199_AST);
			match(GT);
			comparison_op_AST = (AST)currentAST.root;
			break;
		}
		case NOT_EQ:
		{
			AST tmp200_AST = null;
			tmp200_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp200_AST);
			match(NOT_EQ);
			comparison_op_AST = (AST)currentAST.root;
			break;
		}
		case LE:
		{
			AST tmp201_AST = null;
			tmp201_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp201_AST);
			match(LE);
			comparison_op_AST = (AST)currentAST.root;
			break;
		}
		case GE:
		{
			AST tmp202_AST = null;
			tmp202_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp202_AST);
			match(GE);
			comparison_op_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_like:
		{
			AST tmp203_AST = null;
			tmp203_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp203_AST);
			match(LITERAL_like);
			comparison_op_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_not:
		{
			AST tmp204_AST = null;
			tmp204_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp204_AST);
			match(LITERAL_not);
			AST tmp205_AST = null;
			tmp205_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp205_AST);
			match(LITERAL_like);
			if ( inputState.guessing==0 ) {
				comparison_op_AST = (AST)currentAST.root;
				comparison_op_AST = astFactory.create(NOTLIKE,"NOTLIKE");
				currentAST.root = comparison_op_AST;
				currentAST.child = comparison_op_AST!=null &&comparison_op_AST.getFirstChild()!=null ?
					comparison_op_AST.getFirstChild() : comparison_op_AST;
				currentAST.advanceChildToEnd();
			}
			comparison_op_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = comparison_op_AST;
	}

	public final void or_constraint_set() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST or_constraint_set_AST = null;

		safe_abstract_constraint();
		astFactory.addASTChild(currentAST, returnAST);
		{
		int _cnt591=0;
		_loop591:
		do {
			if ((LA(1)==LITERAL_or)) {
				match(LITERAL_or);
				safe_abstract_constraint();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				if ( _cnt591>=1 ) { break _loop591; } else {throw new NoViableAltException(LT(1), getFilename());}
			}

			_cnt591++;
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			or_constraint_set_AST = (AST)currentAST.root;
			or_constraint_set_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(OR_CONSTRAINT_SET,"OR_CONSTRAINT_SET")).add(or_constraint_set_AST));
			currentAST.root = or_constraint_set_AST;
			currentAST.child = or_constraint_set_AST!=null &&or_constraint_set_AST.getFirstChild()!=null ?
				or_constraint_set_AST.getFirstChild() : or_constraint_set_AST;
			currentAST.advanceChildToEnd();
		}
		or_constraint_set_AST = (AST)currentAST.root;
		returnAST = or_constraint_set_AST;
	}

	public final void and_constraint_set() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST and_constraint_set_AST = null;

		safe_abstract_constraint();
		astFactory.addASTChild(currentAST, returnAST);
		{
		int _cnt594=0;
		_loop594:
		do {
			if ((LA(1)==LITERAL_and)) {
				match(LITERAL_and);
				safe_abstract_constraint();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				if ( _cnt594>=1 ) { break _loop594; } else {throw new NoViableAltException(LT(1), getFilename());}
			}

			_cnt594++;
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			and_constraint_set_AST = (AST)currentAST.root;
			and_constraint_set_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(AND_CONSTRAINT_SET,"AND_CONSTRAINT_SET")).add(and_constraint_set_AST));
			currentAST.root = and_constraint_set_AST;
			currentAST.child = and_constraint_set_AST!=null &&and_constraint_set_AST.getFirstChild()!=null ?
				and_constraint_set_AST.getFirstChild() : and_constraint_set_AST;
			currentAST.advanceChildToEnd();
		}
		and_constraint_set_AST = (AST)currentAST.root;
		returnAST = and_constraint_set_AST;
	}

	public final void collection_from_question_mark() throws RecognitionException, TokenStreamException {

		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST collection_from_question_mark_AST = null;

		AST tmp208_AST = null;
		tmp208_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp208_AST);
		match(QUESTION_MARK);
		match(DOT);
		AST tmp210_AST = null;
		tmp210_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp210_AST);
		match(IDENTIFIER);
		if ( inputState.guessing==0 ) {
			collection_from_question_mark_AST = (AST)currentAST.root;
			collection_from_question_mark_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FIELD,"FIELD")).add(collection_from_question_mark_AST));
			currentAST.root = collection_from_question_mark_AST;
			currentAST.child = collection_from_question_mark_AST!=null &&collection_from_question_mark_AST.getFirstChild()!=null ?
				collection_from_question_mark_AST.getFirstChild() : collection_from_question_mark_AST;
			currentAST.advanceChildToEnd();
		}
		collection_from_question_mark_AST = (AST)currentAST.root;
		returnAST = collection_from_question_mark_AST;
	}


	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"IQL_STATEMENT",
		"SELECT_LIST",
		"FROM_LIST",
		"WHERE_CLAUSE",
		"GROUP_CLAUSE",
		"ORDER_CLAUSE",
		"SELECT_VALUE",
		"TABLE_ALIAS",
		"FIELD_ALIAS",
		"TABLE",
		"TABLE_NAME",
		"SUBQUERY",
		"SUBQUERY_LIMIT",
		"CONSTANT",
		"FIELD",
		"FIELD_NAME",
		"SAFE_FUNCTION",
		"UNSAFE_FUNCTION",
		"CONSTRAINT",
		"NOT_CONSTRAINT",
		"AND_CONSTRAINT_SET",
		"OR_CONSTRAINT_SET",
		"SUBQUERY_CONSTRAINT",
		"SUBQUERY_EXISTS_CONSTRAINT",
		"CONTAINS_CONSTRAINT",
		"NOTLIKE",
		"BAG_CONSTRAINT",
		"TYPECAST",
		"FIELD_PATH_EXPRESSION",
		"OBJECTSTOREBAG",
		"ORDER_DESC",
		"BAGS_FOR",
		"COLLECTION_SELECT_LIST",
		"COLLECTION_SELECT_VALUE",
		"\"explain\"",
		"\"select\"",
		"\"all\"",
		"\"distinct\"",
		"COMMA",
		"\"from\"",
		"\"where\"",
		"\"group\"",
		"\"by\"",
		"\"order\"",
		"\"as\"",
		"IDENTIFIER",
		"DOT",
		"OPEN_PAREN",
		"\"union\"",
		"\"intersect\"",
		"\"except\"",
		"\"allbutintersect\"",
		"\"desc\"",
		"COLONTYPE",
		"CLOSE_PAREN",
		"\"bag\"",
		"INTEGER",
		"\"bags\"",
		"\"for\"",
		"\"in\"",
		"QUESTION_MARK",
		"\"limit\"",
		"QUOTED_STRING",
		"FLOAT",
		"\"true\"",
		"\"false\"",
		"\"null\"",
		"\"singleton\"",
		"\"count\"",
		"ASTERISK",
		"\"max\"",
		"\"min\"",
		"\"sum\"",
		"\"avg\"",
		"\"substr\"",
		"\"indexof\"",
		"\"lower\"",
		"\"upper\"",
		"\"stddev\"",
		"\"greatest\"",
		"\"least\"",
		"\"ceil\"",
		"\"floor\"",
		"PLUS",
		"PERCENT",
		"DIVIDE",
		"POWER",
		"MINUS",
		"ISNULL",
		"ISNOTNULL",
		"EQ",
		"NOT_EQ",
		"\"not\"",
		"\"or\"",
		"\"and\"",
		"\"exists\"",
		"\"does\"",
		"\"exist\"",
		"\"contains\"",
		"\"contain\"",
		"LT",
		"GT",
		"LE",
		"GE",
		"\"like\"",
		"SEMI",
		"AT_SIGN",
		"VERTBAR",
		"WS"
	};

	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};

	private static final long[] mk_tokenSet_0() {
		long[] data = { 1155736254373953536L, 8387964L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 1300977342356652032L, 268435324L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 1589207718508363776L, 268435324L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 1589493591531585536L, 268435324L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 1589212116554874880L, 268435324L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 1589695901671096322L, 268435326L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 4472043643653324802L, 133412421503L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 1300977342356652032L, 8387964L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 288257314186592256L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 1732478481654087680L, 107382570493L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { -7633851232410402816L, 34497177321341L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 289844459221286914L, 2L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 4326798157624115202L, 133152374143L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = { -3021918923378393086L, 34497177321343L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = { -4679240287715852286L, 34634616274815L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = { -67554269288464382L, 35184372088703L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { -274877906942L, 35184372088703L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { 1152921504606846976L, 124L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = { 0L, 8387840L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = { 1155736254373953536L, 8388476L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = { 1589489193485074432L, 268435324L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	private static final long[] mk_tokenSet_21() {
		long[] data = { 290398613081686018L, 2L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
	private static final long[] mk_tokenSet_22() {
		long[] data = { 2022111834642841602L, 133152374143L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
	private static final long[] mk_tokenSet_23() {
		long[] data = { 433475862180921344L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
	private static final long[] mk_tokenSet_24() {
		long[] data = { 289923624058486784L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
	private static final long[] mk_tokenSet_25() {
		long[] data = { 1155736254373953536L, 107382570365L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
	private static final long[] mk_tokenSet_26() {
		long[] data = { -7922394694498123776L, 34497177321341L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
	private static final long[] mk_tokenSet_27() {
		long[] data = { -7057702741409267712L, 34634616274813L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
	private static final long[] mk_tokenSet_28() {
		long[] data = { -4751676113753735166L, 35184372088703L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
	private static final long[] mk_tokenSet_29() {
		long[] data = { -139611863326392318L, 35184372088703L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_29 = new BitSet(mk_tokenSet_29());
	private static final long[] mk_tokenSet_30() {
		long[] data = { -7633988396485967870L, 34471407517567L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_30 = new BitSet(mk_tokenSet_30());
	private static final long[] mk_tokenSet_31() {
		long[] data = { -7057144189502357502L, 34634616274815L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_31 = new BitSet(mk_tokenSet_31());
	private static final long[] mk_tokenSet_32() {
		long[] data = { -4751297881753780222L, 35184372088703L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_32 = new BitSet(mk_tokenSet_32());
	private static final long[] mk_tokenSet_33() {
		long[] data = { -7561926404401528830L, 34119220199294L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_33 = new BitSet(mk_tokenSet_33());
	private static final long[] mk_tokenSet_34() {
		long[] data = { -6985087420098150398L, 34222299414399L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_34 = new BitSet(mk_tokenSet_34());
	private static final long[] mk_tokenSet_35() {
		long[] data = { -6985086595464429566L, 34497177321343L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_35 = new BitSet(mk_tokenSet_35());
	private static final long[] mk_tokenSet_36() {
		long[] data = { -8862903746758180862L, 34118951763970L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_36 = new BitSet(mk_tokenSet_36());
	private static final long[] mk_tokenSet_37() {
		long[] data = { -8861777846851338238L, 34118951763970L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_37 = new BitSet(mk_tokenSet_37());
	private static final long[] mk_tokenSet_38() {
		long[] data = { -6986213320004993022L, 34222299414399L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_38 = new BitSet(mk_tokenSet_38());
	private static final long[] mk_tokenSet_39() {
		long[] data = { 1373034936394579968L, 268435324L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_39 = new BitSet(mk_tokenSet_39());
	private static final long[] mk_tokenSet_40() {
		long[] data = { 1589212116554874882L, 268435326L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_40 = new BitSet(mk_tokenSet_40());
	private static final long[] mk_tokenSet_41() {
		long[] data = { 1661727107429957634L, 26038239102L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_41 = new BitSet(mk_tokenSet_41());
	private static final long[] mk_tokenSet_42() {
		long[] data = { 1661823864453201922L, 133412421503L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_42 = new BitSet(mk_tokenSet_42());
	private static final long[] mk_tokenSet_43() {
		long[] data = { -6985087420098150398L, 34497177321343L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_43 = new BitSet(mk_tokenSet_43());
	private static final long[] mk_tokenSet_44() {
		long[] data = { -6985086595464429566L, 34634616274815L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_44 = new BitSet(mk_tokenSet_44());
	private static final long[] mk_tokenSet_45() {
		long[] data = { 1589665115345518594L, 26038239102L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_45 = new BitSet(mk_tokenSet_45());
	private static final long[] mk_tokenSet_46() {
		long[] data = { -7057145014136078334L, 34497177321343L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_46 = new BitSet(mk_tokenSet_46());
	private static final long[] mk_tokenSet_47() {
		long[] data = { -4679240287715852286L, 35184372088703L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_47 = new BitSet(mk_tokenSet_47());
	private static final long[] mk_tokenSet_48() {
		long[] data = { 1300977342356652032L, 16776572L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_48 = new BitSet(mk_tokenSet_48());
	private static final long[] mk_tokenSet_49() {
		long[] data = { -7561618541145751550L, 34119220199294L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_49 = new BitSet(mk_tokenSet_49());
	private static final long[] mk_tokenSet_50() {
		long[] data = { 1300977342356652032L, 25165180L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_50 = new BitSet(mk_tokenSet_50());
	private static final long[] mk_tokenSet_51() {
		long[] data = { 1300977342356652032L, 8388476L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_51 = new BitSet(mk_tokenSet_51());
	private static final long[] mk_tokenSet_52() {
		long[] data = { 1300977342356652032L, 41942396L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_52 = new BitSet(mk_tokenSet_52());
	private static final long[] mk_tokenSet_53() {
		long[] data = { 1300977342356652032L, 75496828L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_53 = new BitSet(mk_tokenSet_53());
	private static final long[] mk_tokenSet_54() {
		long[] data = { 1300977342356652032L, 142605692L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_54 = new BitSet(mk_tokenSet_54());
	private static final long[] mk_tokenSet_55() {
		long[] data = { -8862595883502403582L, 34119211811330L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_55 = new BitSet(mk_tokenSet_55());
	private static final long[] mk_tokenSet_56() {
		long[] data = { -8861469983595560958L, 34119211811330L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_56 = new BitSet(mk_tokenSet_56());
	private static final long[] mk_tokenSet_57() {
		long[] data = { 1589238504833941504L, 268435324L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_57 = new BitSet(mk_tokenSet_57());
	private static final long[] mk_tokenSet_58() {
		long[] data = { 2165980732114075648L, 107642617725L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_58 = new BitSet(mk_tokenSet_58());
	private static final long[] mk_tokenSet_59() {
		long[] data = { -7057390480106979328L, 34497177321341L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_59 = new BitSet(mk_tokenSet_59());
	private static final long[] mk_tokenSet_60() {
		long[] data = { -4751368250497957886L, 34634616274815L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_60 = new BitSet(mk_tokenSet_60());
	private static final long[] mk_tokenSet_61() {
		long[] data = { 2020458169154666496L, 107382570493L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_61 = new BitSet(mk_tokenSet_61());
	private static final long[] mk_tokenSet_62() {
		long[] data = { 289387062384132096L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_62 = new BitSet(mk_tokenSet_62());
	private static final long[] mk_tokenSet_63() {
		long[] data = { 2020739644131377152L, 107382570365L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_63 = new BitSet(mk_tokenSet_63());
	private static final long[] mk_tokenSet_64() {
		long[] data = { 288261162477289472L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_64 = new BitSet(mk_tokenSet_64());
	private static final long[] mk_tokenSet_65() {
		long[] data = { -7922394694498123776L, 34479997452157L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_65 = new BitSet(mk_tokenSet_65());
	private static final long[] mk_tokenSet_66() {
		long[] data = { -7922394694498123776L, 34488587386749L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_66 = new BitSet(mk_tokenSet_66());
	private static final long[] mk_tokenSet_67() {
		long[] data = { -7634164318346412032L, 34497177321341L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_67 = new BitSet(mk_tokenSet_67());
	private static final long[] mk_tokenSet_68() {
		long[] data = { -7057526819548823550L, 34634616274815L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_68 = new BitSet(mk_tokenSet_68());
	private static final long[] mk_tokenSet_69() {
		long[] data = { -7922394694498123776L, 4563402620L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_69 = new BitSet(mk_tokenSet_69());
	private static final long[] mk_tokenSet_70() {
		long[] data = { -7057703566042988544L, 268435325L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_70 = new BitSet(mk_tokenSet_70());
	private static final long[] mk_tokenSet_71() {
		long[] data = { -7057523246136033278L, 30333206399L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_71 = new BitSet(mk_tokenSet_71());
	private static final long[] mk_tokenSet_72() {
		long[] data = { -7057145014136078334L, 133412421503L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_72 = new BitSet(mk_tokenSet_72());
	private static final long[] mk_tokenSet_73() {
		long[] data = { -7634164318346412032L, 268435324L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_73 = new BitSet(mk_tokenSet_73());
	private static final long[] mk_tokenSet_74() {
		long[] data = { -7634159095666180096L, 4563402620L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_74 = new BitSet(mk_tokenSet_74());
	private static final long[] mk_tokenSet_75() {
		long[] data = { -4751852035614179328L, 4563402620L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_75 = new BitSet(mk_tokenSet_75());
	private static final long[] mk_tokenSet_76() {
		long[] data = { -139682232070569984L, 4563402620L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_76 = new BitSet(mk_tokenSet_76());
	private static final long[] mk_tokenSet_77() {
		long[] data = { -139611863326392318L, 133412421503L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_77 = new BitSet(mk_tokenSet_77());
	private static final long[] mk_tokenSet_78() {
		long[] data = { 289532197918998530L, 919123001346L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_78 = new BitSet(mk_tokenSet_78());
	private static final long[] mk_tokenSet_79() {
		long[] data = { 1444525182432575490L, 133152374143L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_79 = new BitSet(mk_tokenSet_79());
	private static final long[] mk_tokenSet_80() {
		long[] data = { -7057145014136078334L, 35046933135231L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_80 = new BitSet(mk_tokenSet_80());
	private static final long[] mk_tokenSet_81() {
		long[] data = { 288406298012155906L, 25769803778L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_81 = new BitSet(mk_tokenSet_81());
	private static final long[] mk_tokenSet_82() {
		long[] data = { 1300977342356652032L, 34093450395516L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_82 = new BitSet(mk_tokenSet_82());
	private static final long[] mk_tokenSet_83() {
		long[] data = { 1589383640368807938L, 17618224283519L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_83 = new BitSet(mk_tokenSet_83());
	private static final long[] mk_tokenSet_84() {
		long[] data = { 1589766270415273986L, 34222299414399L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_84 = new BitSet(mk_tokenSet_84());
	private static final long[] mk_tokenSet_85() {
		long[] data = { -7922394694498123776L, 268435324L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_85 = new BitSet(mk_tokenSet_85());
	private static final long[] mk_tokenSet_86() {
		long[] data = { 2165668470811787264L, 268435324L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_86 = new BitSet(mk_tokenSet_86());
	private static final long[] mk_tokenSet_87() {
		long[] data = { -7634159920299900928L, 268435324L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_87 = new BitSet(mk_tokenSet_87());
	private static final long[] mk_tokenSet_88() {
		long[] data = { -7057699167996477440L, 268435324L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_88 = new BitSet(mk_tokenSet_88());
	private static final long[] mk_tokenSet_89() {
		long[] data = { -7057523246136033278L, 26038239102L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_89 = new BitSet(mk_tokenSet_89());
	private static final long[] mk_tokenSet_90() {
		long[] data = { 1300977342356652032L, 4563402620L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_90 = new BitSet(mk_tokenSet_90());
	private static final long[] mk_tokenSet_91() {
		long[] data = { 2165672868858298368L, 4563402620L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_91 = new BitSet(mk_tokenSet_91());
	private static final long[] mk_tokenSet_92() {
		long[] data = { -7634159920299900928L, 4563402620L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_92 = new BitSet(mk_tokenSet_92());
	private static final long[] mk_tokenSet_93() {
		long[] data = { -7057699167996477440L, 4563402620L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_93 = new BitSet(mk_tokenSet_93());
	private static final long[] mk_tokenSet_94() {
		long[] data = { 1589207718508363776L, 268435325L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_94 = new BitSet(mk_tokenSet_94());
	private static final long[] mk_tokenSet_95() {
		long[] data = { -7633983998439456766L, 26038239102L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_95 = new BitSet(mk_tokenSet_95());
	private static final long[] mk_tokenSet_96() {
		long[] data = { -7633605766439501822L, 133412421503L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_96 = new BitSet(mk_tokenSet_96());
	private static final long[] mk_tokenSet_97() {
		long[] data = { 1589212116554874880L, 4563402621L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_97 = new BitSet(mk_tokenSet_97());
	private static final long[] mk_tokenSet_98() {
		long[] data = { -7633983998439456766L, 30333206398L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_98 = new BitSet(mk_tokenSet_98());
	private static final long[] mk_tokenSet_99() {
		long[] data = { -7634159095666180096L, 268435324L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_99 = new BitSet(mk_tokenSet_99());
	private static final long[] mk_tokenSet_100() {
		long[] data = { -4751852310492086272L, 268435324L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_100 = new BitSet(mk_tokenSet_100());
	private static final long[] mk_tokenSet_101() {
		long[] data = { -139682232070569984L, 268435324L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_101 = new BitSet(mk_tokenSet_101());
	private static final long[] mk_tokenSet_102() {
		long[] data = { 1589212116554874880L, 4563402620L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_102 = new BitSet(mk_tokenSet_102());
	private static final long[] mk_tokenSet_103() {
		long[] data = { -4751852310492086272L, 4563402620L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_103 = new BitSet(mk_tokenSet_103());
	private static final long[] mk_tokenSet_104() {
		long[] data = { 289532197918998530L, 300647710722L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_104 = new BitSet(mk_tokenSet_104());
	private static final long[] mk_tokenSet_105() {
		long[] data = { 289532197918998530L, 644245094402L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_105 = new BitSet(mk_tokenSet_105());
	private static final long[] mk_tokenSet_106() {
		long[] data = { 1300977342356652032L, 536870780L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_106 = new BitSet(mk_tokenSet_106());
	private static final long[] mk_tokenSet_107() {
		long[] data = { 1589383640368807938L, 26038239102L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_107 = new BitSet(mk_tokenSet_107());
	private static final long[] mk_tokenSet_108() {
		long[] data = { 1589766270415273986L, 133680856959L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_108 = new BitSet(mk_tokenSet_108());
	private static final long[] mk_tokenSet_109() {
		long[] data = { 1300977342356652032L, 805306236L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_109 = new BitSet(mk_tokenSet_109());
	private static final long[] mk_tokenSet_110() {
		long[] data = { 1589766270415273986L, 133949292415L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_110 = new BitSet(mk_tokenSet_110());
	private static final long[] mk_tokenSet_111() {
		long[] data = { 289532197918998530L, 28991029250L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_111 = new BitSet(mk_tokenSet_111());
	private static final long[] mk_tokenSet_112() {
		long[] data = { 1300977342356652032L, 34092645089148L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_112 = new BitSet(mk_tokenSet_112());
	private static final long[] mk_tokenSet_113() {
		long[] data = { 1589207718508363776L, 17592454479740L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_113 = new BitSet(mk_tokenSet_113());
	private static final long[] mk_tokenSet_114() {
		long[] data = { 1589388038415319042L, 34118414892926L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_114 = new BitSet(mk_tokenSet_114());
	private static final long[] mk_tokenSet_115() {
		long[] data = { 1589766270415273986L, 34221494108031L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_115 = new BitSet(mk_tokenSet_115());

	}
