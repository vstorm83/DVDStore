/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao.test.persitence;

import java.util.Date;
import net.chrisrichardson.ormunit.hibernate.HibernatePersistenceTests;
import net.homeip.dvdstore.pojo.Movie;
import net.homeip.dvdstore.pojo.MovieCatgory;
import net.homeip.dvdstore.pojo.Order;
import org.junit.Test;

/**
 *
 * @author VU VIET PHUONG
 */
public class OrderPersitenceTest extends HibernatePersistenceTests {
    private Order order;

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();

//        MovieCatgory movCat = new MovieCatgory();
//        movCat.setMovCatName("Loại phim để test");
//
//        Director director = new Director();
//        director.setDirectorName("Steve Bammer để test");
//
//        Actor actor = new Actor();
//        actor.setActorName("Tom Cruise để test");
//
//        Movie movie = new Movie();
//        movie.setMovName("Chiến tranh và hòa bình để test");
//        movie.setMovPicName("test.jpg");
//        movie.setMovDesc("Mô tả phim để test");
//        movie.setMovCat(movCat);
//        movie.setDateCreated(new Date());
//        movie.setDirector(director);
//        movie.setMovPrice(10000);
//        movie.setMovSaleOff(10);
//        movie.addActor(actor);
//        save(movie);
//
//        OrderItem item = new OrderItem();
//        item.setMovie(movie);
//        item.setMovPrice(15000);
//        item.setMovSaleOff(5);
//        item.setQuantity(2);
//
//        User user = new User();
//        user.setUserName("tendangnhap");
//        user.setPassword("123");
//        user.setDeliveryInfo(new DeliveryInfo("Vũ Việt", "Phương", "Hà Nội", "090", "emailfortest@gmail.com"));
//        user.setLock(false);
//        user.setDateCreated(new Date());
//        save(user);
//
//        DeliveryInfo newInfo = new DeliveryInfo("Vũ Ngọc", "Thúy", "Hà Nội", "098", "emailfortest@gmail.com");
//        order = new Order();
//        order.setDeliveryInfo(newInfo);
//        order.setStartDate(new Date());
//        order.addItem(item);
//        order.setPaymentMethod("papal");
//        order.setUser(user);
    }

//    @Test
//    public void test() {
//        Movie movie = new Movie();
//        MovieCatgory movieCat = new MovieCatgory();
//        movieCat.setMovCatName("Loại phim test");
//        movie.setMovCat(movieCat);
//        movie.setDateCreated(new Date());
//        movie.setMovName("tên phim test");
//        movie.setMovPrice(0);
//        movie.setMovSaleOff(0);
//        getHibernateTemplate().save(movie);
//    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:Data.xml"};
    }

    private void add() {
        save(order);
    }
}