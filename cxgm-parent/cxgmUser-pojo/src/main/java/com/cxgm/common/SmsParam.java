package com.cxgm.common;

public class SmsParam {
	
	 //短信模板变量：模板内容中的变量${product}  
    private String product;  
    //短信模板变量：模板内容中的变量${number}，手机验证码  
    private String code;    
    //短信模板变量：模板内容中的变量${ordernumber}，订单号  
    private String ordernumber;  
    //短信模板变量：模板内容中的变量${disname}，关注商品地区  
    private String disname;  
    //短信模板变量：模板内容中的变量${goodsinfoname}，货品名称  
    private String goodsinfoname;  
    //短信模板变量：模板内容中的变量${ofollowprice}，关注商品收藏的价格  
    private String ofollowprice;  
    //短信模板变量：模板内容中的变量${nfollowprice}，降低以后的新价格  
    private String nfollowprice;  
    //短信模板变量：模板内容中的变量${bsetname}，站点名称  
    private String bsetname;  
      
    public SmsParam() {  
        super();  
    }  
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProduct() {  
        return product;  
    }  
    public void setProduct(String product) {  
        this.product = product;  
    }  
    public String getOrdernumber() {  
        return ordernumber;  
    }  
    public void setOrdernumber(String ordernumber) {  
        this.ordernumber = ordernumber;  
    }  
    public String getDisname() {  
        return disname;  
    }  
    public void setDisname(String disname) {  
        this.disname = disname;  
    }  
    public String getGoodsinfoname() {  
        return goodsinfoname;  
    }  
    public void setGoodsinfoname(String goodsinfoname) {  
        this.goodsinfoname = goodsinfoname;  
    }  
    public String getOfollowprice() {  
        return ofollowprice;  
    }  
    public void setOfollowprice(String ofollowprice) {  
        this.ofollowprice = ofollowprice;  
    }  
    public String getNfollowprice() {  
        return nfollowprice;  
    }  
    public void setNfollowprice(String nfollowprice) {  
        this.nfollowprice = nfollowprice;  
    }  
    public String getBsetname() {  
        return bsetname;  
    }  
    public void setBsetname(String bsetname) {  
        this.bsetname = bsetname;  
    }  
}
