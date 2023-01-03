import com.cnlbc.controller.UserController;
import com.cnlbc.domain.Brand;
import com.cnlbc.domain.MultipartFileDto;
import com.cnlbc.domain.Result;
import com.cnlbc.domain.User;
import com.cnlbc.service.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


public class test {
    @Test
    public void test1() throws IOException {
    }
    @Test
    public void testcart() throws IOException {
        ApplicationContext app = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        CartService cartService = (CartService) app.getBean("cartservice");
        if(cartService.deletecart(190))System.out.println(1);
    }

    @Test
    public void testuser() throws IOException {
        ApplicationContext app = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        UserService userService = (UserService) app.getBean("userservice");
        System.out.println(userService.findAllUser());
    }
    @Test
    public void testuser1() throws IOException {
        ApplicationContext app = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        UserService userService = (UserService) app.getBean("userservice");
        System.out.println(userService.save("account","pass","狗扬","999","email"));
    }
    @Test
    public void testbrand() throws IOException {
        ApplicationContext app = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        BrandService brandService = (BrandService) app.getBean("brandservice");
        System.out.println(brandService.findallbrand());

    }
    @Test
    public void testgoods() throws IOException {
        ApplicationContext app = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        GoodsService goodsService = (GoodsService) app.getBean("goodservice");
        System.out.println(goodsService.details(90));
        System.out.println(goodsService.comments(90));
        System.out.println(goodsService.getCategory(90));
//        System.out.println(goodsService.promptKeywords("小"));
    }
    @Test
    public void testaddress() throws IOException {
        ApplicationContext app = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        User_addressService user_addressService = (User_addressService) app.getBean("user_address_service");
        if(user_addressService.updateaddress(25,"我","123456","广东","广州","番禺区","东校","111"))System.out.println(1);
    }
    @Test
    public void testupload() throws IOException
    {
        ApplicationContext app = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        UserService userService = (UserService) app.getBean("userservice");
        File file = new File("D:\\testimg.jpg");
        MultipartFile cMultiFile = new MultipartFileDto("file", file.getName(), null, new FileInputStream(file));
//        System.out.println(userService.upload(cMultiFile ,"my"));
    }
}
