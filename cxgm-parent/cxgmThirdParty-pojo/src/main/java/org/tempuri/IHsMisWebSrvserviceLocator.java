/**
 * IHsMisWebSrvserviceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class IHsMisWebSrvserviceLocator extends org.apache.axis.client.Service implements org.tempuri.IHsMisWebSrvservice {

    public IHsMisWebSrvserviceLocator() {
    }


    public IHsMisWebSrvserviceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IHsMisWebSrvserviceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IHsMisWebSrvPort
    private java.lang.String IHsMisWebSrvPort_address = "http://221.219.243.5:8099/HsMisWebSrv.dll/soap/IHsMisWebSrv";

    public java.lang.String getIHsMisWebSrvPortAddress() {
        return IHsMisWebSrvPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IHsMisWebSrvPortWSDDServiceName = "IHsMisWebSrvPort";

    public java.lang.String getIHsMisWebSrvPortWSDDServiceName() {
        return IHsMisWebSrvPortWSDDServiceName;
    }

    public void setIHsMisWebSrvPortWSDDServiceName(java.lang.String name) {
        IHsMisWebSrvPortWSDDServiceName = name;
    }

    public org.tempuri.IHsMisWebSrv getIHsMisWebSrvPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IHsMisWebSrvPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIHsMisWebSrvPort(endpoint);
    }

    public org.tempuri.IHsMisWebSrv getIHsMisWebSrvPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.IHsMisWebSrvbindingStub _stub = new org.tempuri.IHsMisWebSrvbindingStub(portAddress, this);
            _stub.setPortName(getIHsMisWebSrvPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIHsMisWebSrvPortEndpointAddress(java.lang.String address) {
        IHsMisWebSrvPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.IHsMisWebSrv.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.IHsMisWebSrvbindingStub _stub = new org.tempuri.IHsMisWebSrvbindingStub(new java.net.URL(IHsMisWebSrvPort_address), this);
                _stub.setPortName(getIHsMisWebSrvPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("IHsMisWebSrvPort".equals(inputPortName)) {
            return getIHsMisWebSrvPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "IHsMisWebSrvservice");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "IHsMisWebSrvPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IHsMisWebSrvPort".equals(portName)) {
            setIHsMisWebSrvPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
