/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.webservice;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import javax.jws.WebService;
import net.homeip.dvdstore.dao.OrderDAO;
import net.homeip.dvdstore.pojo.Order;
import net.homeip.dvdstore.pojo.OrderItem;
import net.homeip.dvdstore.pojo.webservice.vo.ExportCardItemVO;
import net.homeip.dvdstore.pojo.webservice.vo.ExportCardVO;
import net.homeip.dvdstore.service.ConfigurationService;
import net.homeip.dvdstore.service.ReportService;

// </editor-fold>
/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.ExportCardPanelService")
public class ExportCardPanelServiceImpl implements ExportCardPanelService {

    private OrderDAO orderDAO;
    private ConfigurationService configurationService;
    private ReportService reportService;

    public byte[] printExportCard(List<Long> exportCardIds) {
        return reportService.renderReport(reportService.EXPORT_CARD,
                findExportCardVOs(exportCardIds), null, null);
    }

    public void revertExportCard(List<Long> exportCardIds) {
        if (exportCardIds == null) {
            throw new IllegalArgumentException("can't revert null exportCardIds");
        }
        Order order;
        for (Long exportCardId : exportCardIds) {
            order = orderDAO.getOrderById(exportCardId);
            order.revert();
            orderDAO.save(order);
        }
    }

    public List<ExportCardVO> findExportCardVOs(List<Long> exportCardIds) {
        List<Order> orders = new ArrayList<Order>(exportCardIds.size());
        for (Long exportCardId : exportCardIds) {
            Order order = orderDAO.getOrderById(exportCardId);
            if (order != null) {
                orders.add(order);
            }
        }
        return makeExportCardVO(orders, TimeZone.getTimeZone(configurationService.getTimeZoneId()));
    }

    public List<ExportCardVO> getExportCardVOs(
            String userNameSearch, Date startDateSearch, Date endDateSearch) {
        TimeZone timeZone = TimeZone.getTimeZone(configurationService.getTimeZoneId());

        GregorianCalendar start = makeSearchDate(startDateSearch, timeZone);
        GregorianCalendar end = makeSearchDate(endDateSearch, timeZone);
        List<Order> orders = orderDAO.findExportCard(userNameSearch,
                start, end);

        List<ExportCardVO> exportCardVOs = makeExportCardVO(orders, timeZone);
        return exportCardVOs;
    }

    private List<ExportCardItemVO> makeExportCardItemVO(Set<OrderItem> orderItems) {
        if (orderItems == null) {
            return null;
        }
        List<ExportCardItemVO> exportCardItemVOs = new ArrayList<ExportCardItemVO>(orderItems.size());
        ExportCardItemVO exportCardItemVO;
        for (OrderItem orderItem : orderItems) {
            exportCardItemVO = new ExportCardItemVO();
            exportCardItemVO.setMovCatName(orderItem.getMovie().getMovCat().getMovCatName());
            exportCardItemVO.setMovId(orderItem.getMovie().getMovId());
            exportCardItemVO.setMovName(orderItem.getMovie().getMovName());
            exportCardItemVO.setMovPrice(orderItem.getMovPrice());
            exportCardItemVO.setMovSaleOff(orderItem.getMovSaleOff());
            exportCardItemVO.setQuantity(orderItem.getQuantity());
            exportCardItemVOs.add(exportCardItemVO);
        }
        return exportCardItemVOs;
    }

    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    private GregorianCalendar makeSearchDate(Date dateSearch, TimeZone timeZone) {
        GregorianCalendar dt = new GregorianCalendar();
        dt.setTimeInMillis(dateSearch.getTime() - timeZone.getRawOffset());
        return dt;
    }

    private List<ExportCardVO> makeExportCardVO(List<Order> orders, TimeZone timeZone) {
        List<ExportCardVO> exportCardVOs = new ArrayList<ExportCardVO>(orders.size());
        ExportCardVO exportCardVO;
        for (Order order : orders) {
            exportCardVO = new ExportCardVO();
            exportCardVO.setAddress(order.getDeliveryInfo().getAddress());
            exportCardVO.setEmail(order.getDeliveryInfo().getEmail());
            exportCardVO.setFirstName(order.getDeliveryInfo().getFirstName());
            exportCardVO.setLastName(order.getDeliveryInfo().getLastName());
            exportCardVO.setExportCardId(order.getOrderId());
            exportCardVO.setExportCardItemVOs(makeExportCardItemVO(order.getOrderItem()));
            exportCardVO.setStartDate(new Date(order.getStartDate().getTime() + timeZone.getRawOffset()));
            exportCardVO.setEndDate(new Date(order.getEndDate().getTime() + timeZone.getRawOffset()));
            exportCardVO.setTel(order.getDeliveryInfo().getTel());
            exportCardVO.setUserName(order.getUser().getUserName());
            exportCardVOs.add(exportCardVO);
        }
        return exportCardVOs;
    }
}
