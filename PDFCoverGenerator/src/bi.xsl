<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="no"/>
	<xsl:param name="chap"/>
	<xsl:param name="verse"/>
	<xsl:template match="/">
		<html>
			<head>
				<style>
					div {width:100%;}
					.dc {color:blue}
					.dv {color:grey;font-size:8pt;vertical-align:top}
				</style>
			</head>
			<body>
			
				<div>
					<xsl:copy-of select="//text[@chap=$chap]"/>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>