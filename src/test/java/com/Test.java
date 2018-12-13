/**  
* Title: Test.java  
* Description:   
* @author zhangyong 
* @date 2018年12月7日  
* @version 1.0  
*/  

package com;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**  
* Title: Test
* Description: 
* @author zhangyong
* @date 2018年12月7日下午3:23:05  
*/

public class Test {
    public static void test1() throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name", "张三");
        map.put("age", 23);
        map.put("addr", "北京");
        ObjectMapper mapper = new ObjectMapper();
        String string = mapper.writeValueAsString(map);
        System.out.println(string);
    }
    public static void main(String[] args) throws Exception {
		test1();
	}
}
