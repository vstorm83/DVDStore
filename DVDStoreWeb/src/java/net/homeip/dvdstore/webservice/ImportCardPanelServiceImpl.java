/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.webservice;

// <editor-fold defaultstate="collapsed" desc="import">
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import javax.jws.WebService;
import net.homeip.dvdstore.dao.ImportCardDAO;
import net.homeip.dvdstore.dao.MovieDAO;
import net.homeip.dvdstore.dao.SupplierDAO;
import net.homeip.dvdstore.pojo.ImportCard;
import net.homeip.dvdstore.pojo.ImportCardItem;
import net.homeip.dvdstore.pojo.Movie;
import net.homeip.dvdstore.pojo.Supplier;
import net.homeip.dvdstore.pojo.webservice.vo.ImportCardItemVO;
import net.homeip.dvdstore.pojo.webservice.vo.ImportCardVO;
import net.homeip.dvdstore.pojo.webservice.vo.InitImportCardPanelVO;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierVO;
import net.homeip.dvdstore.service.ConfigurationService;
import net.homeip.dvdstore.service.ReportService;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;
import org.apache.log4j.Logger;

// </editor-fold>
/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.ImportCardPanelService")
public class ImportCardPanelServiceImpl implements ImportCardPanelService {

    private ImportCardDAO importCardDAO;
    private SupplierDAO supplierDAO;
    private MovieDAO movieDAO;
    private ConfigurationService configurationService;
    private ReportService reportService;
    private Logger logger = Logger.getLogger(ImportCardPanelServiceImpl.class);

    public byte[] printImportCard(List<Long> importCardIds) {
        return reportService.renderReport(reportService.IMPORT_CARD,
                findImportCardVOs(importCardIds), null, null);
    }

    public void updateImportCardItem(ImportCardVO importCardVO) throws InvalidAdminInputException {
        ImportCard importCard = importCardDAO.getImportCardById(importCardVO.getImportCardId());
        importCard.update(importCardVO, supplierDAO);
        importCardDAO.save(importCard);
    }

    public void deleteImportCardItem(Long importCardId, List<Long> movIds) {
        if (importCardId == null || movIds == null) {
            throw new IllegalArgumentException("can't delete null importCardId or movIds");
        }
        ImportCard importCard = importCardDAO.getImportCardById(importCardId);
        if (importCard == null) {
            throw new IllegalStateException("can't delete imporCardItem, importCard not found");
        }

        Iterator<ImportCardItem> importCardItems = importCard.getImportCardItem().iterator();
        ImportCardItem importCardItem;
        while (importCardItems.hasNext()) {
            importCardItem = importCardItems.next();
            for (Long id : movIds) {
                if (importCardItem.getMovie().getMovId().longValue() == id.longValue()) {
                    importCardItems.remove();
                }
            }
        }
        importCardDAO.save(importCard);
    }

    public void addImportCardItem(Long importCardId, List<Long> movIds) {
        ImportCard importCard = importCardDAO.getImportCardById(importCardId);

        Movie movie;
        for (Long movId : movIds) {
            if (addIfItemExists(importCard.getImportCardItem(), movId)) {
                continue;
            }
            //Nếu chưa có phim trong phiếu nhập
            movie = movieDAO.getMovieById(movId);
            if (movie == null) {
                throw new IllegalStateException("Movie not found");
            }
            importCard.getImportCardItem().add(makeImportCardItem(movie));
        }
        importCardDAO.save(importCard);
    }

    public void deleteImportCard(List<Long> importCardIds) {
        if (importCardIds == null) {
            throw new IllegalArgumentException("importCardIds is null");
        }

        List<ImportCard> importCards = new ArrayList<ImportCard>(importCardIds.size());
        for (Long id : importCardIds) {
            importCards.add(importCardDAO.getImportCardById(id));
        }
        importCardDAO.deleteAll(importCards);
    }

    public List<ImportCardVO> getImportCardVOs(String supplierNameSearch,
            Date startDateSearch, Date endDateSearch) {
        logger.trace("getImportCardVOs supplierNameSearch = " + supplierNameSearch);
        GregorianCalendar start = makeSearchDate(startDateSearch);
        GregorianCalendar end = makeSearchDate(endDateSearch);

        List<ImportCard> importCards = importCardDAO.findImportCard(supplierNameSearch,
                start, end);
        logger.trace("getImportCardVOs importCards.size()=" + importCards.size());
        return makeImportCardVOs(importCards);
    }

    public List<ImportCardVO> findImportCardVOs(List<Long> importCardIds) {
        List<ImportCard> importCards = new ArrayList<ImportCard>(importCardIds.size());
        for (Long importCardId : importCardIds) {
            ImportCard importCard = importCardDAO.getImportCardById(importCardId);
            if (importCard != null) {
                importCards.add(importCard);
            }
        }

        return makeImportCardVOs(importCards);
    }

    public InitImportCardPanelVO getInitImportCardPanelVO(String supplierNameSearch,
            Date startDateSearch, Date endDateSearch) {
        InitImportCardPanelVO initImportCardPanelVO = new InitImportCardPanelVO();
        initImportCardPanelVO.setSupplierVOs(makeSupplierVOs(supplierDAO.findSupplier()));

        initImportCardPanelVO.setImportCardVOs(
                getImportCardVOs(supplierNameSearch, startDateSearch, endDateSearch));
        logger.trace("getImportCardVOs initImportCardPanelVO.size="
                + initImportCardPanelVO.getImportCardVOs().size());
        return initImportCardPanelVO;
    }

    public void addImportCard(SupplierVO supplierVO) {
        if (supplierVO == null) {
            throw new IllegalArgumentException("can't add importCard with null supplierVO");
        }
        Supplier supplier = supplierDAO.getSupplierById(supplierVO.getSupplierId());
        if (supplier == null) {
            throw new IllegalStateException("can't add importCard, supplier not found");
        }
        ImportCard importCard = importCardDAO.createImportCard();
        importCard.populate(supplier, configurationService.getTimeZoneId());
        importCardDAO.save(importCard);
    }

    private boolean addIfItemExists(Set<ImportCardItem> importCardItems, Long movId) {
        for (ImportCardItem importCardItem : importCardItems) {
            if (importCardItem.getMovie().getMovId().longValue() == movId.longValue()) {
                importCardItem.setQuantity(importCardItem.getQuantity() + 1);
                return true;
            }
        }
        return false;
    }

    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public void setImportCardDAO(ImportCardDAO importCardDAO) {
        this.importCardDAO = importCardDAO;
    }

    public void setSupplierDAO(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    public void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    // <editor-fold defaultstate="collapsed" desc="make">
    private List<ImportCardVO> makeImportCardVOs(List<ImportCard> importCards) {
        TimeZone timeZone = TimeZone.getTimeZone(configurationService.getTimeZoneId());
        if (importCards == null) {
            return null;
        }
        List<ImportCardVO> importCardVOs = new ArrayList<ImportCardVO>(importCards.size());
        ImportCardVO importCardVO;
        for (ImportCard importCard : importCards) {
            importCardVO = new ImportCardVO();
            importCardVO.setImportCardId(importCard.getImportCardId());
            importCardVO.setSupplierVO(makeSupplierVO(importCard.getSupplier()));
            importCardVO.setImportCardItemVOs(makeImporCardItemVOs(importCard.getImportCardItem()));
            importCardVO.setDateCreated(
                    new Date(importCard.getDateCreated().getTime() + timeZone.getRawOffset()));
            importCardVOs.add(importCardVO);
        }
        return importCardVOs;
    }

    private List<ImportCardItemVO> makeImporCardItemVOs(Set<ImportCardItem> importCardItems) {
        if (importCardItems == null) {
            return null;
        }
        List<ImportCardItemVO> importCardItemVOs =
                new ArrayList<ImportCardItemVO>(importCardItems.size());
        ImportCardItemVO importCardItemVO;
        for (ImportCardItem importCardItem : importCardItems) {
            importCardItemVO = new ImportCardItemVO();
            importCardItemVO.setMovCatName(importCardItem.getMovie().getMovCat().getMovCatName());
            importCardItemVO.setMovId(importCardItem.getMovie().getMovId());
            importCardItemVO.setMovName(importCardItem.getMovie().getMovName());
            importCardItemVO.setMovPrice(importCardItem.getMovPrice());
            importCardItemVO.setQuantity(importCardItem.getQuantity());
            importCardItemVOs.add(importCardItemVO);
        }
        return importCardItemVOs;
    }

    private List<SupplierVO> makeSupplierVOs(List<Supplier> suppliers) {
        List<SupplierVO> voList = new ArrayList<SupplierVO>(suppliers.size());
        SupplierVO vo;
        for (Supplier supplier : suppliers) {
            vo = new SupplierVO();
            vo.setSupplierId(supplier.getSupplierId());
            vo.setSupplierName(supplier.getSupplierName());
            voList.add(vo);
        }
        return voList;
    }

    private SupplierVO makeSupplierVO(Supplier supplier) {
        if (supplier == null) {
            return null;
        }
        SupplierVO supplierVO = new SupplierVO();
        supplierVO.setSupplierId(supplier.getSupplierId());
        supplierVO.setSupplierName(supplier.getSupplierName());
        return supplierVO;
    }

    private ImportCardItem makeImportCardItem(Movie movie) {
        ImportCardItem importCardItem = new ImportCardItem();
        importCardItem.setMovie(movie);
        importCardItem.setMovPrice(movie.getMovPrice());
        importCardItem.setQuantity(1);
        return importCardItem;
    }
    // </editor-fold>

    private GregorianCalendar makeSearchDate(Date dateSearch) {
        TimeZone timeZone = TimeZone.getTimeZone(configurationService.getTimeZoneId());
        GregorianCalendar dt = new GregorianCalendar();
        dt.setTimeInMillis(dateSearch.getTime() - timeZone.getRawOffset());        
        return dt;
    }
}
