<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited by XMLSpy® -->
<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns="http://www.w3.org/1999/xhtml">
<xsl:output
method="html"
doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
doctype-system= 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"/>  
<xsl:template match="/">
  <html>
  <body>
  <h2>TimeTable</h2>
    <table border="1">
      <tr bgcolor="#9acd32">
        <th>Sunday</th>
        <th>Monday</th>
		<th>Thusday</th>
		<th>Wednsday</th>
		<th>Thursday</th>
		<th>Friday</th>
		<th>Saturday</th>
	  </tr>
      <tr>
      <xsl:for-each select="timeTable/day">
		  <td><xsl:value-of select="hour[position() = 1]"/></td>
      </xsl:for-each>
      </tr>
      <tr>
      <xsl:for-each select="timeTable/day">
           <td><xsl:value-of select="hour[position() = 2]"/></td>
      </xsl:for-each>
      </tr>
      <tr>
      <xsl:for-each select="timeTable/day">
           <td><xsl:value-of select="hour[position() = 3]"/></td>
      </xsl:for-each>
      </tr>
      <tr>
      <xsl:for-each select="timeTable/day">
           <td><xsl:value-of select="hour[position() = 4]"/></td>
      </xsl:for-each>
      </tr>
      <tr>
      <xsl:for-each select="timeTable/day">
           <td><xsl:value-of select="hour[position() = 5]"/></td>
      </xsl:for-each>
      </tr>
	  <tr>
      <xsl:for-each select="timeTable/day">
          <td><xsl:value-of select="hour[position() = 6]"/></td>
      </xsl:for-each>
      </tr>
	  <tr>
      <xsl:for-each select="timeTable/day">
           <td><xsl:value-of select="hour[position() = 7]"/></td>
      </xsl:for-each>
      </tr>
	  <tr>
      <xsl:for-each select="timeTable/day">
           <td><xsl:value-of select="hour[position() = 8]"/></td>
      </xsl:for-each>
      </tr>
	  <tr>
      <xsl:for-each select="timeTable/day">
           <td><xsl:value-of select="hour[position() = 9]"/></td>
      </xsl:for-each>
      </tr>
    </table>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>

