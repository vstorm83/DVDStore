/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao.test.persitence;

import java.util.Date;
import net.chrisrichardson.ormunit.hibernate.HibernatePersistenceTests;
import net.homeip.dvdstore.pojo.Actor;
import net.homeip.dvdstore.pojo.Director;
import net.homeip.dvdstore.pojo.ImportCard;
import net.homeip.dvdstore.pojo.ImportCardItem;
import net.homeip.dvdstore.pojo.Movie;
import net.homeip.dvdstore.pojo.MovieCatgory;
import net.homeip.dvdstore.pojo.Order;
import net.homeip.dvdstore.pojo.OrderItem;
import net.homeip.dvdstore.pojo.Supplier;
import net.homeip.dvdstore.pojo.User;
import net.homeip.dvdstore.pojo.web.vo.DeliveryInfo;
import org.junit.Test;

/**
 *
 * @author VU VIET PHUONG
 */
public class ImportCardPersitenceTest extends HibernatePersistenceTests {
    private ImportCard importCard;

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();

        MovieCatgory movCat = new MovieCatgory();
        movCat.setMovCatName("Hành động");

        Director director = new Director();
        director.setDirectorName("Steve Bammer");

        Actor actor = new Actor();
        actor.setActorName("Tom Cruise");

        Movie movie = new Movie();
        movie.setMovName("Chiến tranh và hòa bình");
        movie.setMovPicName("test.jpg");
        movie.setMovDesc("Mô tả phim");
        movie.setMovCat(movCat);
        movie.setDateCreated(new Date());
        movie.setDirector(director);
        movie.setMovPrice(10000);
        movie.setMovSaleOff(10);
        movie.addActor(actor);
        save(movie);

        ImportCardItem importCardItem = new ImportCardItem();
        importCardItem.setMovie(movie);
        importCardItem.setMovPrice(100000);
        importCardItem.setQuantity(10);

        Supplier supplier = new Supplier();
        supplier.setSupplierName("Hồng hà");

        importCard = new ImportCard();
        importCard.setDateCreated(new Date());
        importCard.setSupplier(supplier);
        importCard.getImportCardItem().add(importCardItem);
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:Data.xml"};
    }

    @Test
    public void testPersitence() {
        add();
        update();
        delete(importCard);
    }

    private void add() {
        save(importCard);
    }

    private void update() {
        importCard.setDateCreated(new Date());
    }
}
