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
  		<h1>Weekly schedule list</h1>
 <xsl:for-each select="timeTable/day">
 <h2>day <xsl:value-of select="@value"/> courses:</h2>
  			<xsl:for-each select="child::*">
			 <h4>hour <xsl:value-of select="@value"/>  <xsl:value-of select="."/> </h4>
		    </xsl:for-each>
</xsl:for-each>
</body>
</html>
</xsl:template>
</xsl:stylesheet>

