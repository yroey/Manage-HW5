<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited by XMLSpy® -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

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
          <td><xsl:value-of select="hour1"/></td>
      </xsl:for-each>
      </tr>
      <tr>
      <xsl:for-each select="timeTable/day">
           <td><xsl:value-of select="hour2"/></td>
      </xsl:for-each>
      </tr>
      <tr>
      <xsl:for-each select="timeTable/day">
           <td><xsl:value-of select="hour3"/></td>
      </xsl:for-each>
      </tr>
      <tr>
      <xsl:for-each select="timeTable/day">
           <td><xsl:value-of select="hour4"/></td>
      </xsl:for-each>
      </tr>
      <tr>
      <xsl:for-each select="timeTable/day">
           <td><xsl:value-of select="hour5"/></td>
      </xsl:for-each>
      </tr>
	  <tr>
      <xsl:for-each select="timeTable/day">
           <td><xsl:value-of select="hour6"/></td>
      </xsl:for-each>
      </tr>
	  <tr>
      <xsl:for-each select="timeTable/day">
           <td><xsl:value-of select="hour7"/></td>
      </xsl:for-each>
      </tr>
	  <tr>
      <xsl:for-each select="timeTable/day">
           <td><xsl:value-of select="hour8"/></td>
      </xsl:for-each>
      </tr>
	  <tr>
      <xsl:for-each select="timeTable/day">
           <td><xsl:value-of select="hour9"/></td>
      </xsl:for-each>
      </tr>
    </table>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>

