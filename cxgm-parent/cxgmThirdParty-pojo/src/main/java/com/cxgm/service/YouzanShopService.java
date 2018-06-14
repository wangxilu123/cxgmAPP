package com.cxgm.service;

import java.util.Arrays;
import java.util.List;

import org.apache.http.entity.ContentType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.client.oauth.model.OAuthToken;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanMultistoreOfflineSearch;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanMultistoreOfflineSearchParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanMultistoreOfflineSearchResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanMultistoreOfflineSearchResult.AccountShopOffline;
import com.youzan.open.sdk.util.http.DefaultHttpClient;
import com.youzan.open.sdk.util.http.HttpClient;
import com.youzan.open.sdk.util.json.JsonUtils;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * 有赞业务对接 User: CQL
 *
 */
@Primary
@Service
public class YouzanShopService {

	/**
	 * 获取门店列表信息接口
	 */
	public static List<AccountShopOffline> findYouzanShopList() {
		
		OAuthToken oAuthToken = getToken();

		@SuppressWarnings("resource")
		YZClient client = new DefaultYZClient(new Token(oAuthToken.getAccessToken()));
		YouzanMultistoreOfflineSearchParams youzanMultistoreOfflineSearchParams = new YouzanMultistoreOfflineSearchParams();

		YouzanMultistoreOfflineSearch youzanMultistoreOfflineSearch = new YouzanMultistoreOfflineSearch();
		youzanMultistoreOfflineSearch.setAPIParams(youzanMultistoreOfflineSearchParams);
		YouzanMultistoreOfflineSearchResult result = client.invoke(youzanMultistoreOfflineSearch);
		
		List<AccountShopOffline> list = Arrays.asList(result.getList());
		
		return list;
	}
	
       public static OAuthToken  getToken() {

        @SuppressWarnings("resource")
		HttpClient httpClient = new DefaultHttpClient();
           HttpClient.Params params = HttpClient.Params.custom()

                 .add("client_id", "0f5c2247ff164b541e")  
                 .add("client_secret", "4b7cf80d9c5c91503c8b9cb058c1552a")                    
                 .add("grant_type", "silent")            
                 .add("kdt_id","40643391")
           .setContentType(ContentType.APPLICATION_FORM_URLENCODED).build();
           String resp = httpClient.post("https://open.youzan.com/oauth/token", params);
           System.out.println(resp);
           
		return  JsonUtils.toBean(resp, new TypeReference<OAuthToken>(){});
    }
       
}
