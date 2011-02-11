/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.dao;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import net.homeip.dvdstore.pojo.ImportCard;
import net.homeip.dvdstore.util.TextUtil;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author VU VIET PHUONG
 */
public class ImportCardDAOImpl extends HibernateDaoSupport implements ImportCardDAO {

    public List<ImportCard> findImportCardBySupplierId(Long supplierId) {
        return getHibernateTemplate().findByNamedQueryAndNamedParam("findImportCardBySupplierId",
                "supplierId", supplierId);
    }

    public List<ImportCard> findImportCardByMovieId(Long movId) {
        return getHibernateTemplate().findByNamedQueryAndNamedParam("findImportCardByMovieId",
                "movId", movId);
    }

    public List<ImportCard> findImportCard(String supplierNameSearch,
            GregorianCalendar startDateSearch, GregorianCalendar endDateSearch) {
        makeSearchDate(startDateSearch);
        makeSearchDate(endDateSearch);
        endDateSearch.add(Calendar.DATE, 1);

        return getHibernateTemplate().findByNamedQueryAndNamedParam(
                "findImportCard", new String[]{"supplierNameSearch", "startDateSearch", "endDateSearch"},
                new Object[]{"%" + TextUtil.normalize(supplierNameSearch) + "%", startDateSearch.getTime(),
                    endDateSearch.getTime()});
    }

    public ImportCard createImportCard() {
        return new ImportCard();
    }

    public void deleteAll(List<ImportCard> importCards) {
        getHibernateTemplate().deleteAll(importCards);
    }

    public ImportCard getImportCardById(Long importCardId) {
        return (ImportCard)getHibernateTemplate().get(ImportCard.class, importCardId);
    }

    public void save(ImportCard importCard) {
        getHibernateTemplate().saveOrUpdate(importCard);
    }

    private void makeSearchDate(GregorianCalendar dt) {
        dt.set(Calendar.HOUR, 0);
        dt.set(Calendar.MINUTE, 0);
        dt.set(Calendar.SECOND, 0);
        dt.set(Calendar.MILLISECOND, 0);
    }
}
