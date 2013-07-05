#!/usr/bin/python

from pyparsing import makeHTMLTags,withAttribute,Suppress,Regex,Group
import os.path

""" looking for this recurring pattern:
  <td valign="top" bgcolor="#FFFFCC">0-3</td>
  <td valign="top">.50</td>
  <td valign="top">.50</td>
    
  and want a dict with keys 0, 1, 2, and 3 all with values (.50,.50)
"""
td,tdend = makeHTMLTags("td")
keytd = td.copy().setParseAction(withAttribute(bgcolor="#FFFFCC"))
td,tdend,keytd = map(Suppress,(td,tdend,keytd))

realnum = Regex(r'1?\.\d+').setParseAction(lambda t:float(t[0]))
integer = Regex(r'\d{1,3}').setParseAction(lambda t:int(t[0]))
DASH = Suppress('-')

# build up an expression matching the HTML bits above
entryExpr = (keytd + integer("start") + DASH + integer("end") + tdend + 
                Group(2*(td + realnum + tdend))("vals"))

# search the input HTML for matches to the entryExpr expression, and build up lookup dict
lookup = {}
for entry in entryExpr.searchString(open(os.path.dirname(__file__) + '/../fide.html').read()):
  for i in range(entry.start, entry.end+1):
    lookup[i] = tuple(entry.vals)

# print the first column of the dictionary to a text file
f = open('expected.txt', 'w')
# we only need expected change up to a rating difference of 400
for i in range(0,401):
  f.write(str(lookup[i][0]) + ", ")
f.close()
