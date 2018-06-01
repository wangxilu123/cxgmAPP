package org.tempuri;

public class IHsMisWebSrvProxy implements org.tempuri.IHsMisWebSrv {
  private String _endpoint = null;
  private org.tempuri.IHsMisWebSrv iHsMisWebSrv = null;
  
  public IHsMisWebSrvProxy() {
    _initIHsMisWebSrvProxy();
  }
  
  public IHsMisWebSrvProxy(String endpoint) {
    _endpoint = endpoint;
    _initIHsMisWebSrvProxy();
  }
  
  private void _initIHsMisWebSrvProxy() {
    try {
      iHsMisWebSrv = (new org.tempuri.IHsMisWebSrvserviceLocator()).getIHsMisWebSrvPort();
      if (iHsMisWebSrv != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iHsMisWebSrv)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iHsMisWebSrv)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iHsMisWebSrv != null)
      ((javax.xml.rpc.Stub)iHsMisWebSrv)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.IHsMisWebSrv getIHsMisWebSrv() {
    if (iHsMisWebSrv == null)
      _initIHsMisWebSrvProxy();
    return iHsMisWebSrv;
  }
  
  public java.lang.String testWeb() throws java.rmi.RemoteException{
    if (iHsMisWebSrv == null)
      _initIHsMisWebSrvProxy();
    return iHsMisWebSrv.testWeb();
  }
  
  public int IHiOpenedIntf(java.lang.String pIntfCode, java.lang.String pInData, javax.xml.rpc.holders.StringHolder pOutData) throws java.rmi.RemoteException{
    if (iHsMisWebSrv == null)
      _initIHsMisWebSrvProxy();
    return iHsMisWebSrv.IHiOpenedIntf(pIntfCode, pInData, pOutData);
  }
  
  
}