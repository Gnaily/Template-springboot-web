package site.yl.template;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Description 单元测试基类
 * 			    @BeforeClass ==> @Before ==> @Test ==> @After ==> @AfterClass 
 * @author yangliang
 * @see  <a href="https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#boot-features-testing">
 *       Spring Test</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
		classes=Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

public abstract class BaseApplicationTest {
	
	
	// 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。
	protected MockMvc mockMvc;   
	  
	// 注入WebApplicationContext 
  @Autowired
  protected  WebApplicationContext wac;

    
  //在测试开始前初始化工作
	@Before 
  public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();  
    }  
	
	
 	@Before
  public void init() {
	    System.out.println("===============================测试开始===============================");
    }
 	
 	@After
  public void after() {
	    System.out.println("===============================测试结束===============================");
    }





    //------------------------------------------utility functions-----------------------------------

    @FunctionalInterface
    public interface IAction {
	    void on(MockHttpServletRequestBuilder requestBuilder) throws Exception;
    }

     /**
     * 循环执行多个请求上
     * @param requestBuilders  请求构造器数组
     * @param apply            对每个请求应用的函数
     * @throws Exception
     */
    public void execute(MockHttpServletRequestBuilder[]  requestBuilders, IAction apply) {

        for (MockHttpServletRequestBuilder request:requestBuilders) {
        	try {
		        apply.on(request);
	        }catch (Exception e){
        		e.printStackTrace();
	        }
        }
    }
}
