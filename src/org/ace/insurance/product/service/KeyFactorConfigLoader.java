package org.ace.insurance.product.service;

import java.io.CharArrayWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.ace.insurance.product.KeyFactorConfig;
import org.ace.insurance.product.ReferenceItem;
import org.ace.insurance.product.service.interfaces.IKeyFactorConfigLoader;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@Service("KeyFactorConfigLoader")
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class KeyFactorConfigLoader extends DefaultHandler implements IKeyFactorConfigLoader {
	private CharArrayWriter text = new CharArrayWriter();
	private Map<String, KeyFactorConfig> configMap = new HashMap<String, KeyFactorConfig>();
	private List<ReferenceItem> referenceItemList = new ArrayList<ReferenceItem>();

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("reference")) {
			String id = attributes.getValue("id");
			String entity = attributes.getValue("entity");
			String keyColumn = attributes.getValue("keyColumn");
			String displayColumn = attributes.getValue("displayColumn");
			String condition = attributes.getValue("condition");
			referenceItemList.add(new ReferenceItem(id, entity));
			configMap.put(entity, new KeyFactorConfig(id, entity, keyColumn, displayColumn, condition));
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {

	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		text.write(ch, start, length);
	}

	@PostConstruct
	public void load() {
		try {
			Resource configFile = new ClassPathResource("keyfactor-reference.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(configFile.getFile(), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public KeyFactorConfig getKeyFactorConfig(String entityName) {
		return configMap.get(entityName);
	}

	public List<ReferenceItem> getReferenceItemList() {
		return referenceItemList;
	}
}
