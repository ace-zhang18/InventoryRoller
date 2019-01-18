<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text" omit-xml-declaration="yes" indent="no"/>
<xsl:template match="/">
<xsl:if test="/inventory/master_discount/discount">
<xsl:text>master_discount
</xsl:text>
</xsl:if>
<xsl:for-each select="/inventory/master_discount/discount">
<xsl:value-of select="@rate"/><xsl:text>
</xsl:text>
<xsl:value-of select="@weight"/><xsl:text>
</xsl:text>
</xsl:for-each>
<xsl:for-each select="/inventory/item">item
<xsl:value-of select="name"/><xsl:text>
</xsl:text>
<xsl:value-of select="price/@min"/><xsl:text>
</xsl:text>
<xsl:value-of select="price/@max"/><xsl:text>
</xsl:text>
<xsl:value-of select="amount/@min"/><xsl:text>
</xsl:text>
<xsl:value-of select="amount/@max"/><xsl:text>
</xsl:text>
<xsl:value-of select="weight"/><xsl:text>
</xsl:text>


<xsl:if test="not(/inventory/master_discount/discount)">
<xsl:for-each select="discount">
<xsl:value-of select="@rate"/><xsl:text>
</xsl:text>
<xsl:value-of select="@weight"/><xsl:text>
</xsl:text>
</xsl:for-each>
</xsl:if>

</xsl:for-each>
</xsl:template>

</xsl:stylesheet>