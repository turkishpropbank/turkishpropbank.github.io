/**
 * SweNLP is a framework for performing parallel processing of text. 
 * Copyright © 2011 Peter Exner
 * 
 * This file is part of SweNLP.
 *
 * SweNLP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SweNLP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SweNLP.  If not, see <http://www.gnu.org/licenses/>.
 */
 
 package base;

%%

%class SwedishTokenizer
%unicode
%integer
%function getNextToken
%pack
%char

%{

public static final int ALPHANUM = 0;
public static final int APOSTROPHE = 1;
public static final int ACRONYM = 2;
public static final int COMPANY = 3;
public static final int EMAIL = 4;
public static final int HOST = 5;
public static final int NUM = 6;
public static final int CJ = 7;
public static final int ACRONYM_DEP = 8;
public static final int PUNCTUATION = 9;
public static final int PARENTHESIS = 10;
public static final int COMPOSITE_WORD = 11;
public static final int COLON = 12;
public static final int MATH_SYMBOL = 13;
public static final int ABBREVIATION = 14;
public static final int ENDING = 15;
public static final int SINGLE_QUOTE = 16;
public static final int SPLIT_WORD = 17;
public static final int CONJUNCTION = 18;
public static final int DOTDOTDOT = 19;
public static final int ABBREVIATION_SWEDISH = 20;
public static final int ABBREVIATEDYEAR = 21;
public static final int LISTITEM = 22;
public static final int PARAGRAPH = 23;
public static final int ACRONYM_SINGLE = 24;
public static final int ABBREVIATION_SWEDISH_MONTHS = 25;

%}

THAI       = [\u0E00-\u0E59]

// basic word: a sequence of digits & letters (includes Thai to enable ThaiAnalyzer to function)
ALPHANUM   = ({LETTER}|{THAI}|[:digit:])+

// internal apostrophes: O'Reilly, you're, O'Reilly's
// use a post-filter to remove possessives
APOSTROPHE =  {ALPHA} ("'" |(("'"|":") {ALPHA})+)

// acronyms: U.S.A., I.B.M., etc.
// use a post-filter to remove dots
ACRONYM    =  {LETTER} "." ({LETTER} ".")+

ACRONYM_DEP	= {ALPHANUM} "." ({ALPHA} ".")+

ACRONYM_SINGLE = [a-zA-Z] "."

// composite words: A-inkomster, etc.
COMPOSITE_WORD = {ALPHA} ("-" {ALPHA})+

// split words: trivsel- (och)
SPLIT_WORD = {ALPHANUM} "-"

// abbreviations: t_ex, etc.
ABBREVIATION = {ALPHA} ("_" {ALPHA})+

// company names like AT&T and Excite@Home.
COMPANY    =  {ALPHA} ("&"|"@") {ALPHA}

// email addresses
EMAIL      =  {ALPHANUM} (("."|"-"|"_") {ALPHANUM})* "@" {ALPHANUM} (("."|"-") {ALPHANUM})+

// hostname
HOST       =  {ALPHANUM} ((".") {ALPHANUM})+

// floating point, serial, model numbers, ip addresses, etc.
// every other segment must have at least one digit
NUM        = ({ALPHANUM} {P} {HAS_DIGIT}
           | {HAS_DIGIT} {P} {ALPHANUM}
           | {ALPHANUM} ({P} {HAS_DIGIT} {P} {ALPHANUM})+
           | {HAS_DIGIT} ({P} {ALPHANUM} {P} {HAS_DIGIT})+
           | {ALPHANUM} {P} {HAS_DIGIT} ({P} {ALPHANUM} {P} {HAS_DIGIT})+
           | {HAS_DIGIT} {P} {ALPHANUM} ({P} {HAS_DIGIT} {P} {ALPHANUM})+)


// abbreviated year: -67
ABBREVIATEDYEAR = "-" [:digit:] [:digit:]

// numbered list item: 1., 2., etc.
NUMBERLISTITEM = ([:digit:]".")


// numbered/alphabetical list item with parentheses: (a), (1), etc.
ALPHALISTITEM = ("(" ([0-9]|[a-zA-Z]) ")")

// parenthesis
PARENTHESIS = ("("|")")

// colon
COLON = (":"|";")

// mathematic symbols
MATH_SYMBOL = ("="|"+"|"%"|"*")

// single quote
SINGLE_QUOTE = ("'")

// paragraph
PARAGRAPH = "§"

// ending: !, ?
ENDING = ("!"|"?")

// punctuation
P	         = ("_"|"-"|"/"|"."|","|":")

// Swedish conjunctions
CONJUNCTION = ("och"|"eller"|","|"/")

// ...
DOTDOTDOT = "..."

// Swedish abbreviations
ABBREVIATION_SWEDISH = ([Dd]"oc."|"dvs."|"etc."|"ff."|"fig."|[Kk]"ap."|"kl."|"Kor."|[Kk]"ungl."|"milj."|"Mos."|"Mt."|"mån."|"proc."|[Pp]"rof."|"resp."|"s."|"sid."|"tr."|"tel."|[Vv]"ol.")

// Swedish abbreviations of months
ABBREVIATION_SWEDISH_MONTHS = ("jan."|"feb."|"mar."|"apr."|"maj."|"jun."|"jul."|"aug."|"sep."|"okt."|"nov."|"dec.")

// at least one digit
HAS_DIGIT  = ({LETTER}|[:digit:])* [:digit:] ({LETTER}|[:digit:])*

ALPHA      = ({LETTER})+

// From the JFlex manual: "the expression that matches everything of <a> not matched by <b> is !(!<a>|<b>)"
LETTER     = !(![:letter:]|{CJ})

// Chinese and Japanese (but NOT Korean, which is included in [:letter:])
CJ         = [\u3100-\u312f\u3040-\u309F\u30A0-\u30FF\u31F0-\u31FF\u3300-\u337f\u3400-\u4dbf\u4e00-\u9fff\uf900-\ufaff\uff65-\uff9f]

WHITESPACE = \r\n | [ \r\n\t\f]


%%

{ALPHANUM}										{ return ALPHANUM; }
{APOSTROPHE}									{ return APOSTROPHE; }
{ACRONYM}										{ return ACRONYM; }
{ACRONYM_SINGLE} / [^\r\n\f)]					{ return ACRONYM_SINGLE; }
{COMPOSITE_WORD}								{ return COMPOSITE_WORD; }
{SPLIT_WORD} / {WHITESPACE}+{CONJUNCTION}		{ return SPLIT_WORD; }
{SPLIT_WORD} / {WHITESPACE}+{ALPHANUM}			{ return SPLIT_WORD; }
{SPLIT_WORD} / {PARENTHESIS}					{ return SPLIT_WORD; }
{ABBREVIATION}									{ return ABBREVIATION; }
{COMPANY}										{ return COMPANY; }
{EMAIL}											{ return EMAIL; }
{HOST}											{ return HOST; }
{NUM}											{ return NUM; }
{ABBREVIATEDYEAR}								{ return ABBREVIATEDYEAR; }
{CJ}											{ return CJ; }
{ACRONYM_DEP}									{ return ACRONYM_DEP; }
{NUMBERLISTITEM} [^\r\n\f)]						{ return LISTITEM; }
{ALPHALISTITEM}									{ return LISTITEM; }
{PARENTHESIS}									{ return PARENTHESIS; }
{COLON}											{ return COLON; }
{MATH_SYMBOL}									{ return MATH_SYMBOL; }
{SINGLE_QUOTE}									{ return SINGLE_QUOTE; }
{PARAGRAPH}										{ return PARAGRAPH; }
{DOTDOTDOT}										{ return DOTDOTDOT; }
{ABBREVIATION_SWEDISH} / [^\r\n\f)]				{ return ABBREVIATION_SWEDISH; }
{ABBREVIATION_SWEDISH_MONTHS} / [^\r\n\f)]		{ return ABBREVIATION_SWEDISH_MONTHS; }
{ENDING}										{ return ENDING; }
{P}												{ return PUNCTUATION; }						


/** Ignore the rest */
. | {WHITESPACE}	{ /* Break so we don't hit fall-through warning: */ break;/* ignore */ }