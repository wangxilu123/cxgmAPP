package com.cxgmerp.common.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class GeneratorPO {

	public static void main(String[] args) throws Exception {
		GeneratorPO ge = new GeneratorPO();
		String url = ge.getClass().getResource("").getPath();
		System.out.println(url.toString());
		ge.generator(url);

	}
	public void generator(String url) throws Exception{

	    List<String> warnings = new ArrayList<String>();
	    boolean overwrite = true;
	    //加载generatorConfig.xml文件
	    File configFile = new File(url+"/generatorConfig.xml"); 
	    ConfigurationParser cp = new ConfigurationParser(warnings);
	    Configuration config = cp.parseConfiguration(configFile);
	    DefaultShellCallback callback = new DefaultShellCallback(overwrite);
	    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
	            callback, warnings);
	    myBatisGenerator.generate(null);

	} 
}
