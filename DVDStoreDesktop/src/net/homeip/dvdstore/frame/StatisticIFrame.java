/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StatisticIFrame.java
 *
 * Created on Feb 24, 2010, 5:28:17 PM
 */
package net.homeip.dvdstore.frame;

// <editor-fold defaultstate="collapsed" desc="import">
import java.awt.BorderLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.homeip.dvdstore.ApplicationContextUtil;
import net.homeip.dvdstore.listener.MovieCatgoryChangeListener;
import net.homeip.dvdstore.listener.MovieChangeListener;
import net.homeip.dvdstore.listener.SupplierChangeListener;
import net.homeip.dvdstore.listener.UserChangeListener;
import net.homeip.dvdstore.listener.event.MovieCatgoryChangeEvent;
import net.homeip.dvdstore.listener.event.MovieChangeEvent;
import net.homeip.dvdstore.listener.event.SupplierChangeEvent;
import net.homeip.dvdstore.listener.event.UserChangeEvent;
import net.homeip.dvdstore.listener.frame.StatisticIFrameListener;
import net.homeip.dvdstore.panel.ExportCardPanel;
import net.homeip.dvdstore.panel.ImportCardPanel;

// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class StatisticIFrame extends javax.swing.JInternalFrame
        implements MovieCatgoryChangeListener, MovieChangeListener, 
        SupplierChangeListener, UserChangeListener {

    private ExportCardPanel exportCardPanel;
    private ImportCardPanel importCardPanel;
    private StatisticIFrameListener statisticIFrameListener;
    public static final String EXPORT_CARD_TAB = "Phiếu xuất lưu";
    public static final String IMPORT_CARD_TAB = "Quản lý phiếu nhập";

    /** Creates new form StatisticIFrame */
    public StatisticIFrame(String tabTitle) {
        initComponents();

        statisticIFrameListener =
                (StatisticIFrameListener) ApplicationContextUtil.getApplicationContext().getBean(
                "statisticIFrameListener");
        prepareListenerRegistry();
        
        prepareTabbedPane(tabTitle);
    }

    public void onMovieCatgoryChange(MovieCatgoryChangeEvent evt) {
        if (exportCardPanel != null) {
            exportCardPanel.onMovieCatgoryChange(evt);
        }
        if (importCardPanel != null) {
            importCardPanel.onMovieCatgoryChange(evt);
        }
    }

    public void onMovieChange(MovieChangeEvent evt) {
        if (exportCardPanel != null) {
            exportCardPanel.onMovieChange(evt);
        }
        if (importCardPanel != null) {
            importCardPanel.onMovieChange(evt);
        }
    }

    public void onSupplierChange(SupplierChangeEvent evt) {
        if (importCardPanel != null) {
            importCardPanel.onSupplierChange(evt);
        }
    }

    public void onUserChange(UserChangeEvent evt) {
        if (exportCardPanel != null) {
            exportCardPanel.onUserChange(evt);
        }
    }

    private void prepareListenerRegistry() {
        addInternalFrameListener(statisticIFrameListener);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel statisticIFrameHeaderPane = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel lblTieuDe = new javax.swing.JLabel();
        statisticTabbedPane = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        exportCardPanelPlaceHolder = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        importCardPanelPlaceHolder = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("DVDStoreDesktop"); // NOI18N
        setTitle(bundle.getString("StatisticIFrame.title")); // NOI18N

        statisticIFrameHeaderPane.setLayout(new java.awt.BorderLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/goctrai.gif"))); // NOI18N
        statisticIFrameHeaderPane.add(jLabel1, java.awt.BorderLayout.WEST);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gocfai.gif"))); // NOI18N
        statisticIFrameHeaderPane.add(jLabel2, java.awt.BorderLayout.EAST);

        lblTieuDe.setBackground(new java.awt.Color(255, 255, 255));
        lblTieuDe.setFont(new java.awt.Font("Times New Roman", 3, 24));
        lblTieuDe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTieuDe.setText(bundle.getString("StatisticIFrame.lblTieuDe.text")); // NOI18N
        lblTieuDe.setOpaque(true);
        statisticIFrameHeaderPane.add(lblTieuDe, java.awt.BorderLayout.CENTER);

        getContentPane().add(statisticIFrameHeaderPane, java.awt.BorderLayout.NORTH);

        exportCardPanelPlaceHolder.setPreferredSize(new java.awt.Dimension(100, 100));
        exportCardPanelPlaceHolder.setLayout(new java.awt.BorderLayout());
        jScrollPane2.setViewportView(exportCardPanelPlaceHolder);

        statisticTabbedPane.addTab(bundle.getString("StatisticIFrame.jScrollPane2.TabConstraints.tabTitle"), jScrollPane2); // NOI18N

        importCardPanelPlaceHolder.setLayout(new java.awt.BorderLayout());
        jScrollPane1.setViewportView(importCardPanelPlaceHolder);

        statisticTabbedPane.addTab(bundle.getString("StatisticIFrame.jScrollPane1.TabConstraints.tabTitle"), jScrollPane1); // NOI18N

        getContentPane().add(statisticTabbedPane, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1067)/2, (screenSize.height-658)/2, 1067, 658);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel exportCardPanelPlaceHolder;
    private javax.swing.JPanel importCardPanelPlaceHolder;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane statisticTabbedPane;
    // End of variables declaration//GEN-END:variables

    public void activeTab(String tabTitle) {
        if (tabTitle == null) {
            throw new IllegalArgumentException("tabTitle is null");
        }
        createTab(tabTitle);
        for (int idx = 0; idx < statisticTabbedPane.getTabCount(); idx++) {
            if (statisticTabbedPane.getTitleAt(idx).equals(tabTitle)) {
                statisticTabbedPane.setSelectedIndex(idx);
            }
        }
    }

    private void createTab(String tabTitle) {
        if (tabTitle.equals(EXPORT_CARD_TAB) && exportCardPanel == null) {
            exportCardPanel = new ExportCardPanel();
            exportCardPanelPlaceHolder.add(exportCardPanel, BorderLayout.CENTER);            
        } else if (tabTitle.equals(IMPORT_CARD_TAB) && importCardPanel == null) {
            importCardPanel = new ImportCardPanel();
            importCardPanelPlaceHolder.add(importCardPanel, BorderLayout.CENTER);            
        }
    }

    private void prepareTabbedPane(String tabTitle) {
        activeTab(tabTitle);
        statisticTabbedPane.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                int selectedIdx = statisticTabbedPane.getSelectedIndex();
                String tabTitle = statisticTabbedPane.getTitleAt(selectedIdx);
                createTab(tabTitle);
            }
        });
    }
}
