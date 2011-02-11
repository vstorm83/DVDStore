/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo;

/**
 *
 * @author VU VIET PHUONG
 */
public class ImportCardItem {
    private Movie movie;
    private double movPrice;
    private int quantity;

    public double getMovPrice() {
        return movPrice;
    }

    public void setMovPrice(double movPrice) {
        this.movPrice = movPrice;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImportCardItem other = (ImportCardItem) obj;
        if (this.movie != other.movie && (this.movie == null || !this.movie.equals(other.movie))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.movie != null ? this.movie.hashCode() : 0);
        return hash;
    }
    
}
