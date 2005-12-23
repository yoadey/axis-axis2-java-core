package org.apache.axis2.description;

import org.apache.axis2.wsdl.builder.WSDLComponentFactory;
import org.apache.axis2.wsdl.java2wsdl.SchemaGenerator;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.wsdl.*;
import org.apache.wsdl.extensions.*;
import org.apache.wsdl.extensions.impl.SOAPAddressImpl;
import org.apache.wsdl.impl.WSDLDescriptionImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
/*
* Copyright 2004,2005 The Apache Software Foundation.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* @author : Deepal Jayasinghe (deepal@apache.org)
*
*/

public class AxisService2WOM {

    private XmlSchema schema;
    private AxisService axisService;
    private String url;


    public AxisService2WOM(XmlSchema schema, AxisService service,
                           String targentNamespece,
                           String targetNamespecheprefix, String serviceURL) {
        this.schema = schema;
        this.axisService = service;
        url = serviceURL;

        if (targentNamespece != null && !targentNamespece.trim().equals("")) {
            SchemaGenerator.TARGET_NAMESPACE = targentNamespece;
        }
        if (targetNamespecheprefix != null && !targetNamespecheprefix.trim().equals("")) {
            SchemaGenerator.TARGET_NAMESPACE_PREFIX = targetNamespecheprefix;
        }

    }

    public WSDLDescription generateWOM() throws Exception {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
        StringWriter writer = new StringWriter();
        schema.write(writer);
        writer.flush();
        Document doc = domBuilder.parse(new ByteArrayInputStream(writer.toString().getBytes()));
        Element documentElement = doc.getDocumentElement();
        WSDLDescription womDescription;
        WSDLComponentFactory wsdlComponentFactory = new WSDLDescriptionImpl();
        womDescription = wsdlComponentFactory.createDescription();
        HashMap namespaceMap = new HashMap();
        namespaceMap.put("soap", "http://schemas.xmlsoap.org/wsdl/soap/");
        namespaceMap.put(SchemaGenerator.TARGET_NAMESPACE_PREFIX, SchemaGenerator.TARGET_NAMESPACE);
        namespaceMap.put("ns1", "http://org.apache.axis2/xsd");
        namespaceMap.put("xs", "http://www.w3.org/2001/XMLSchema");
        womDescription.setNamespaces(namespaceMap);
        womDescription.setTargetNameSpace(SchemaGenerator.TARGET_NAMESPACE);

        //generating port type
        WSDLInterface portType = generatePortType(womDescription, wsdlComponentFactory, documentElement);
        womDescription.addInterface(portType);

        QName bindingName = new QName(SchemaGenerator.TARGET_NAMESPACE, axisService.getName() + "Binding"
                , SchemaGenerator.TARGET_NAMESPACE_PREFIX);
        //generating binding
        WSDLBinding binding = generateBinding(wsdlComponentFactory,
                portType,
                bindingName,
                "document", "literal", "http://schemas.xmlsoap.org/soap/http",
                "http://www.org.apache.axis2");
        womDescription.addBinding(binding);

        //generating axisService
        WSDLService service = generateService(wsdlComponentFactory, binding,
                axisService.getName(), url);
        womDescription.addService(service);
        return womDescription;
    }

    private WSDLInterface generatePortType(WSDLDescription womDescription,
                                           WSDLComponentFactory wsdlComponentFactory,
                                           Element documentElement) {
        WSDLTypes wsdlTypes = wsdlComponentFactory.createTypes();
        ExtensionFactory extensionFactory = wsdlComponentFactory.createExtensionFactory();
        org.apache.wsdl.extensions.Schema schemaExtensibilityElement =
                (org.apache.wsdl.extensions.Schema) extensionFactory.getExtensionElement(
                        ExtensionConstants.SCHEMA);
        wsdlTypes.addExtensibilityElement(schemaExtensibilityElement);
        schemaExtensibilityElement.setElement(documentElement);
        womDescription.setTypes(wsdlTypes);

        WSDLInterface portType = womDescription.createInterface();
        portType.setName(new QName(axisService.getName() + "Port"));


        Iterator operations = axisService.getOperations().values().iterator();
        while (operations.hasNext()) {
            AxisOperation axisOperation = (AxisOperation) operations.next();
            WSDLOperation wsdlOperation = womDescription.createOperation();
            wsdlOperation.setName(axisOperation.getName());

            AxisMessage inaxisMessage = axisOperation.getMessage(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
            if (inaxisMessage != null) {
                MessageReference messageRefinput = wsdlComponentFactory.createMessageReference();
                messageRefinput.setElementQName(inaxisMessage.getElementQName());
                messageRefinput.setDirection(org.apache.wsdl.WSDLConstants.WSDL_MESSAGE_DIRECTION_IN);
                wsdlOperation.setInputMessage(messageRefinput);
            }

            AxisMessage outaxisMessage = axisOperation.getMessage(WSDLConstants.MESSAGE_LABEL_OUT_VALUE);
            if (outaxisMessage != null && outaxisMessage.getElementQName() != null) {
                MessageReference messageRefout = wsdlComponentFactory.createMessageReference();
                messageRefout.setElementQName(outaxisMessage.getElementQName());
                messageRefout.setDirection(org.apache.wsdl.WSDLConstants.WSDL_MESSAGE_DIRECTION_OUT);
                wsdlOperation.setOutputMessage(messageRefout);
            }
            portType.setOperation(wsdlOperation);

        }
        return portType;
    }

    private WSDLService generateService(WSDLComponentFactory wsdlComponentFactory,
                                        WSDLBinding binding, String ServiceName, String URL) {
        WSDLService service = wsdlComponentFactory.createService();
        service.setName(new QName(ServiceName));
        WSDLEndpoint endpoints = wsdlComponentFactory.createEndpoint();
        endpoints.setBinding(binding);
        endpoints.setName(new QName(ServiceName + "PortType"));
        SOAPAddressImpl address = new SOAPAddressImpl();
        address.setLocationURI(URL);
        endpoints.addExtensibilityElement(address);
        service.setEndpoint(endpoints);
        return service;
    }


    private WSDLBinding generateBinding(WSDLComponentFactory wsdlComponentFactory,
                                        WSDLInterface portType, QName bindingName,
                                        String style,
                                        String use,
                                        String trsportURI,
                                        String namespeceURI) {
        WSDLBinding binding = wsdlComponentFactory.createBinding();

        ExtensionFactory extensionFactory = wsdlComponentFactory.createExtensionFactory();

        binding.setBoundInterface(portType);
        binding.setName(bindingName);

        SOAPBinding soapbindingImpl = (SOAPBinding) extensionFactory.getExtensionElement(
                ExtensionConstants.SOAP_11_BINDING);
        soapbindingImpl.setStyle(style);
        soapbindingImpl.setTransportURI(trsportURI);
        binding.addExtensibilityElement(soapbindingImpl);

        Iterator op_itr = portType.getOperations().keySet().iterator();
        while (op_itr.hasNext()) {
            String opName = (String) op_itr.next();
            WSDLOperation wsdlOperation = portType.getOperation(opName);
            MessageReference inMessage = wsdlOperation.getInputMessage();

            WSDLBindingOperation bindingoperation = wsdlComponentFactory.createWSDLBindingOperation();
            bindingoperation.setName(new QName(opName));
            bindingoperation.setOperation(wsdlOperation);
            binding.addBindingOperation(bindingoperation);

            SOAPOperation soapOpimpl = (SOAPOperation) extensionFactory.getExtensionElement(
                    ExtensionConstants.SOAP_11_OPERATION);
            soapOpimpl.setStyle(style);
            //to do heve to set a proper SOAPAction
            soapOpimpl.setSoapAction(opName);
            bindingoperation.addExtensibilityElement(soapOpimpl);
            if (inMessage != null) {
                WSDLBindingMessageReference bindingInMessage = wsdlComponentFactory.createWSDLBindingMessageReference();
                bindingInMessage.setDirection(org.apache.wsdl.WSDLConstants.WSDL_MESSAGE_DIRECTION_IN);
                bindingoperation.setInput(bindingInMessage);

                SOAPBody requestSoapbody = (SOAPBody) extensionFactory.getExtensionElement(
                        ExtensionConstants.SOAP_11_BODY);
                requestSoapbody.setUse(use);
                //todo need to fix this
                requestSoapbody.setNamespaceURI(namespeceURI);
                bindingInMessage.addExtensibilityElement(requestSoapbody);
            }

            MessageReference outMessage = wsdlOperation.getOutputMessage();
            if (outMessage != null) {
                WSDLBindingMessageReference bindingOutMessage = wsdlComponentFactory.createWSDLBindingMessageReference();

                bindingOutMessage.setDirection(org.apache.wsdl.WSDLConstants.WSDL_MESSAGE_DIRECTION_OUT);
                bindingoperation.setOutput(bindingOutMessage);
                SOAPBody resSoapbody = (SOAPBody) extensionFactory.getExtensionElement(
                        ExtensionConstants.SOAP_11_BODY);
                resSoapbody.setUse(use);
                resSoapbody.setNamespaceURI(namespeceURI);
                bindingOutMessage.addExtensibilityElement(resSoapbody);
            }
        }
        return binding;
    }


}

