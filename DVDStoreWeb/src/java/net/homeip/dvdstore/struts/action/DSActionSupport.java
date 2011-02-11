/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import net.homeip.dvdstore.pojo.web.vo.AdvertismentVO;
import net.homeip.dvdstore.pojo.web.vo.ChatVO;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.pojo.web.vo.CompactMovieVO;
import net.homeip.dvdstore.service.AdvertismentService;
import net.homeip.dvdstore.service.ChatService;
import net.homeip.dvdstore.service.ConfigurationService;
import net.homeip.dvdstore.webservice.MovieCatgoryService;
import net.homeip.dvdstore.service.MovieService;
import net.homeip.dvdstore.service.PlaceOrderService;

/**
 *
 * @author VU VIET PHUONG
 */
public abstract class DSActionSupport extends ActionSupport implements DSPreparer {

    private MovieCatgoryService movieCatgoryService;
    private MovieService movieService;
    private AdvertismentService advertismentService;
    private ChatService chatService;
    private PlaceOrderService placeOrderService;
    private ConfigurationService configurationService;
    private List<MovieCatgoryVO> movieCatgoryList;
    private List<AdvertismentVO> advertismentList;
    private List<CompactMovieVO> bestMovieList;
    private List<ChatVO> chatList;
    private int shoppingCartItemCount;
    private String comName;
//    searchError dùng cho searchForm (tìm kiếm bằng tên phim, diễn viên)
//    nếu lỗi validation thì dùng action redirect sang Home action, sẽ mất error
//    message, lúc redirect, set searchError bằng querystring param
    private String searchError;

    @Override
    public void prepareDS() {
        prepareMovieCatgory();
        prepareAdvertisment();
        prepareBestMovie();
        prepareChat();
        prepareShoppingCartSymbol();
        prepareBottomMenu();
    }

    // <editor-fold defaultstate="collapsed" desc="prepare methods">
    private void prepareMovieCatgory() {
        movieCatgoryList = movieCatgoryService.getMovieCatgoryList();
    }

    private void prepareAdvertisment() {
        advertismentList = advertismentService.getAdvertismentList();
    }

    private void prepareBestMovie() {
        bestMovieList = movieService.findBestMovie(configurationService.getBestMovieNum());
    }

    private void prepareChat() {
        chatList = chatService.getChatList();
    }

    private void prepareBottomMenu() {
        comName = configurationService.getCompanyInfo().getComName();
    }

    protected void prepareShoppingCartSymbol() {
        shoppingCartItemCount = placeOrderService.getShoppingCartItemCount();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="get data methods">
    public List<MovieCatgoryVO> getMovieCatgoryList() {
        return movieCatgoryList;
    }

    public List<AdvertismentVO> getAdvertismentList() {
        return advertismentList;
    }

    public List<CompactMovieVO> getBestMovieList() {
        return bestMovieList;
    }

    public List<ChatVO> getChatList() {
        return chatList;
    }

    public String getSearchError() {
        return searchError;
    }

    public void setSearchError(String searchError) {
        this.searchError = searchError;
    }

    public int getShoppingCartItemCount() {
        return shoppingCartItemCount;
    }

    public String getComName() {
        return comName;
    }    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="service methods">
    public MovieService getMovieService() {
        return movieService;
    }

    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    public AdvertismentService getAdvertismentService() {
        return advertismentService;
    }

    public void setAdvertismentService(AdvertismentService advertismentService) {
        this.advertismentService = advertismentService;
    }

    public ChatService getChatService() {
        return chatService;
    }

    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    public PlaceOrderService getPlaceOrderService() {
        return placeOrderService;
    }

    public void setPlaceOrderService(PlaceOrderService placeOrderService) {
        this.placeOrderService = placeOrderService;
    }

    public MovieCatgoryService getMovieCatgoryService() {
        return movieCatgoryService;
    }

    public void setMovieCatgoryService(MovieCatgoryService movieCatgoryService) {
        this.movieCatgoryService = movieCatgoryService;
    }

    public ConfigurationService getConfigurationService() {
        return configurationService;
    }

    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }// </editor-fold>
}
