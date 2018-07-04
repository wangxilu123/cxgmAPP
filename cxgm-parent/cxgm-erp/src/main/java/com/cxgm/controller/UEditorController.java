package com.cxgm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.baidu.ueditor.ActionEnter;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.cxgm.service.UEditorService;

@RestController
public class UEditorController {
	
	@Autowired
	UEditorService ueditorService;
	
	@RequestMapping(value="/config",method = RequestMethod.GET)  
    public void config(HttpServletRequest request, HttpServletResponse response) {  
        response.setContentType("application/json");  
        String rootPath =ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/UEditor/jsp";
        try { 
        	response.setCharacterEncoding("UTF-8");
            String exec = new ActionEnter(request, rootPath).exec();  
            PrintWriter writer = response.getWriter(); 
            writer.write(new org.json.JSONTokener(exec).nextValue().toString());  
            writer.flush();  
            writer.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (JSONException e) {
			e.printStackTrace();
		}  
  
    }  
	
	/**
     * ueditor上传图片
     */
    @RequestMapping(value = "/uploadimages", method = RequestMethod.POST)
    @ResponseBody
    public String uploadup(MultipartHttpServletRequest  request){
    	State state = null;
    	state = new BaseState(true);
    	List<MultipartFile> files = (List<MultipartFile>) request.getFiles("upfile");
        try {
        	if(files!=null){
                for(int i=0;i<files.size();i++){  
                    MultipartFile file = files.get(i);
                    String imgUrl = ueditorService.uploadImages(file);
                     state.putInfo("url", imgUrl);
                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String resultState = state.toJSONString();
        return resultState;
    }
}
