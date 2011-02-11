/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BussinessIFrame.java
 *
 * Created on Feb 23, 2010, 8:54:42 PM
 */
package net.homeip.dvdstore.frame;

// <editor-fold defaultstate="collapsed" desc="import">
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import net.homeip.dvdstore.ApplicationContextUtil;
import net.homeip.dvdstore.dialog.ChooseMovieDialog;
import net.homeip.dvdstore.listener.MovieCatgoryChangeListener;
import net.homeip.dvdstore.listener.MovieChangeListener;
import net.homeip.dvdstore.listener.ResultRetrievedListener;
import net.homeip.dvdstore.listener.UserChangeListener;
import net.homeip.dvdstore.listener.event.LoadDataEvent;
import net.homeip.dvdstore.listener.event.MovieCatgoryChangeEvent;
import net.homeip.dvdstore.listener.event.MovieChangeEvent;
import net.homeip.dvdstore.listener.event.ResultRetrievedEvent;
import net.homeip.dvdstore.listener.event.UserChangeEvent;
import net.homeip.dvdstore.listener.frame.BussinessIFrameListener;
import net.homeip.dvdstore.listener.frame.ChangeOrderEvent;
import net.homeip.dvdstore.listener.frame.OrderCompletedEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.OrderVO;
import net.homeip.dvdstore.swing.renderer.OrderItemTableCellRenderer;
import net.homeip.dvdstore.swing.OrderItemTableModel;
import net.homeip.dvdstore.swing.renderer.OrderTableCellRenderer;
import net.homeip.dvdstore.swing.OrderTableModel;
import net.homeip.dvdstore.swing.editor.DeliveryInfoEditor;
import net.homeip.dvdstore.swing.editor.OrderItemEditor;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

// </editor-fold>
/**
 *
 * @author VU VIET PHUONG
 */
public class BussinessIFrame extends javax.swing.JInternalFrame
        implements MovieCatgoryChangeListener, MovieChangeListener,
        MessageListener, UserChangeListener {

    private LoadDataModel<List<OrderVO>> loadDataModel;
    private DataChangingModel orderChangingModel;
    private BussinessIFrameListener bussinessIFrameListener;
    private ChangeOrderEvent changeOrderEvent;
    private SimpleMessageListenerContainer orderMessageListenerContainer;

    /** Creates new form BussinessIFrame */
    public BussinessIFrame() {
        initComponents();
        prepareTable();

        bussinessIFrameListener =
                (BussinessIFrameListener) ApplicationContextUtil.getApplicationContext().getBean(
                "bussinessIFrameListener");
        prepareListenerRegistry();

        prepareLoadData();
        dispatchLoadDataEvent();

        prepareChangingOrder();
        prepareListenToMessage();
    }

    // <editor-fold defaultstate="collapsed" desc="load Data">
    private void prepareLoadData() {
        loadDataModel = new LoadDataModel<List<OrderVO>>();
        loadDataModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onLoadDataResultRetrieved(evt);
            }
        });
    }

    private void dispatchLoadDataEvent() {
        saveState();
        setControlStatus(DISABLE_ALL);

        LoadDataEvent evt = new LoadDataEvent(this);
        evt.setLoadDataModel(loadDataModel);
        bussinessIFrameListener.onLoadDataPerformed(evt);
    }

    private void onLoadDataResultRetrieved(ResultRetrievedEvent evt) {
        String errorMsg = checkError(loadDataModel.getStatus());
        if (errorMsg != null) {
            JOptionPane.showMessageDialog(this, errorMsg);
        } else {
            orderTableModel.setRowData(loadDataModel.getData());
        }
        restoreState();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="change Order">
    private void dispatchChangOrderEvent(int action, Object params) {
        saveState();
        setControlStatus(DISABLE_ALL);

        changeOrderEvent = new ChangeOrderEvent(this);
        changeOrderEvent.setAction(action);
        changeOrderEvent.setOrderChangingModel(orderChangingModel);
        changeOrderEvent.setParams(params);
        bussinessIFrameListener.onChangeOrderPerformed(changeOrderEvent);
    }

    private void prepareChangingOrder() {
        orderChangingModel = new DataChangingModel();
        orderChangingModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onOrderChangingResultRetrieved(evt);
            }
        });
    }

    private void onOrderChangingResultRetrieved(ResultRetrievedEvent evt) {
        int status = orderChangingModel.getStatus();
        String errorMsg = checkError(status);
        //Không cần check ValidateFail do check lúc sửa bảng rồi
        if (errorMsg != null) {
            restoreState();
            JOptionPane.showMessageDialog(this, errorMsg);
        } else if (changeOrderEvent.getAction() == ChangeOrderEvent.COMPLETE_ORDER) {
            if (status == DataChangingModel.CONSTRAINT_VIOLATION) {
                if (JOptionPane.showConfirmDialog(this, makeConstraintViolationMsg(),
                        "Lưu phiếu xuất", JOptionPane.YES_NO_OPTION)
                        == JOptionPane.YES_OPTION) {
                    reDispatchChangingEvent();
                } else {
                    restoreState();
                }
            } else {
                dispatchOrderCompletedEvent();
            }
        } else {
            dispatchLoadDataEvent();
        }
    }

    private void reDispatchChangingEvent() {
        changeOrderEvent.setIgnoreQuantity(true);
        bussinessIFrameListener.onChangeOrderPerformed(changeOrderEvent);
    }

    private void dispatchOrderCompletedEvent() {
        OrderCompletedEvent orderCompletedEvent = new OrderCompletedEvent(this);
        bussinessIFrameListener.onOrderCompleted(orderCompletedEvent);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="check error">
    private String checkError(int status) {
        String errorMsg = null;
        switch (status) {
            case LoadDataModel.SERVER_CONNECTION_ERROR:
                errorMsg = "Lỗi kết nối Server";
                break;
            case LoadDataModel.SERVER_ERROR:
                errorMsg = "Lỗi Server";
                break;
        }
        return errorMsg;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="prepareListenToMessage">
    private void prepareListenToMessage() {
        addInternalFrameListener(new InternalFrameAdapter() {

            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                orderMessageListenerContainer.shutdown();
            }

            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                try {
                    orderMessageListenerContainer =
                            (SimpleMessageListenerContainer) ApplicationContextUtil.getApplicationContext().getBean(
                            "orderMessageListenerContainer");
                    orderMessageListenerContainer.setMessageListener(BussinessIFrame.this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void onMessage(Message msg) {
        try {
            final OrderVO newOrder = (OrderVO) ((ObjectMessage) msg).getObject();
            EventQueue.invokeLater(new Runnable() {

                public void run() {                    
                    orderTableModel.addOrder(newOrder);                    
                }
            });
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }// </editor-fold>
    
    public void onMovieCatgoryChange(MovieCatgoryChangeEvent evt) {
        dispatchLoadDataEvent();
    }

    public void onMovieChange(MovieChangeEvent evt) {
        dispatchLoadDataEvent();
    }

    public void onUserChange(UserChangeEvent evt) {
        orderTableModel.updateUserInOrder(evt.getOldUserVO(), evt.getNewUserVO());
    }

    private String makeConstraintViolationMsg() {
        StringBuilder supplierNameBuilder = new StringBuilder("Các phim: \n");
        for (String movName : orderChangingModel.getViolationName()) {
            supplierNameBuilder.append(movName);
            supplierNameBuilder.append("\n");
        }
        supplierNameBuilder.append("không đủ số lượng kho, tiếp tục lưu ?");
        return supplierNameBuilder.toString();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel bussinessIFrameHeaderPane = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel lblTieuDeBussiness = new javax.swing.JLabel();
        orderListTabbedPane = new javax.swing.JTabbedPane();
        javax.swing.JSplitPane orderListSplitPane = new javax.swing.JSplitPane();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JPanel orderListPaneNorth = new javax.swing.JPanel();
        orderListScrollPane = new javax.swing.JScrollPane();
        javax.swing.JPanel orderListControlButtonPane = new javax.swing.JPanel();
        btnSaveExportCard = new javax.swing.JButton();
        btnRemoveMovie = new javax.swing.JButton();
        btnDeleteOrder = new javax.swing.JButton();
        btnAddMovie = new javax.swing.JButton();
        javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
        javax.swing.JTabbedPane movieOrderListTabbedPane = new javax.swing.JTabbedPane();
        movieOrderListScrollPane = new javax.swing.JScrollPane();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("DVDStoreDesktop"); // NOI18N
        setTitle(bundle.getString("BussinessIFrame.title")); // NOI18N

        bussinessIFrameHeaderPane.setLayout(new java.awt.BorderLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/goctrai.gif"))); // NOI18N
        bussinessIFrameHeaderPane.add(jLabel1, java.awt.BorderLayout.WEST);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gocfai.gif"))); // NOI18N
        bussinessIFrameHeaderPane.add(jLabel2, java.awt.BorderLayout.EAST);

        lblTieuDeBussiness.setBackground(new java.awt.Color(255, 255, 255));
        lblTieuDeBussiness.setFont(new java.awt.Font("Times New Roman", 3, 24));
        lblTieuDeBussiness.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTieuDeBussiness.setText(bundle.getString("BussinessIFrame.lblTieuDeBussiness.text")); // NOI18N
        lblTieuDeBussiness.setOpaque(true);
        bussinessIFrameHeaderPane.add(lblTieuDeBussiness, java.awt.BorderLayout.CENTER);

        getContentPane().add(bussinessIFrameHeaderPane, java.awt.BorderLayout.NORTH);

        orderListSplitPane.setDividerLocation(300);
        orderListSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(100, 53));

        orderListPaneNorth.setPreferredSize(new java.awt.Dimension(100, 49));
        orderListPaneNorth.setLayout(new java.awt.BorderLayout());
        orderListPaneNorth.add(orderListScrollPane, java.awt.BorderLayout.CENTER);

        orderListControlButtonPane.setBackground(new java.awt.Color(255, 255, 255));

        btnSaveExportCard.setText(bundle.getString("BussinessIFrame.btnSaveExportCard.text")); // NOI18N
        btnSaveExportCard.setEnabled(false);
        btnSaveExportCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveExportCardActionPerformed(evt);
            }
        });

        btnRemoveMovie.setText(bundle.getString("BussinessIFrame.btnRemoveMovie.text")); // NOI18N
        btnRemoveMovie.setEnabled(false);
        btnRemoveMovie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveMovieActionPerformed(evt);
            }
        });

        btnDeleteOrder.setText(bundle.getString("BussinessIFrame.btnDeleteOrder.text")); // NOI18N
        btnDeleteOrder.setEnabled(false);
        btnDeleteOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteOrderActionPerformed(evt);
            }
        });

        btnAddMovie.setText(bundle.getString("BussinessIFrame.btnAddMovie.text")); // NOI18N
        btnAddMovie.setEnabled(false);
        btnAddMovie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMovieActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout orderListControlButtonPaneLayout = new javax.swing.GroupLayout(orderListControlButtonPane);
        orderListControlButtonPane.setLayout(orderListControlButtonPaneLayout);
        orderListControlButtonPaneLayout.setHorizontalGroup(
            orderListControlButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, orderListControlButtonPaneLayout.createSequentialGroup()
                .addContainerGap(621, Short.MAX_VALUE)
                .addComponent(btnSaveExportCard)
                .addGap(18, 18, 18)
                .addComponent(btnAddMovie)
                .addGap(18, 18, 18)
                .addComponent(btnRemoveMovie)
                .addGap(18, 18, 18)
                .addComponent(btnDeleteOrder)
                .addGap(11, 11, 11))
        );
        orderListControlButtonPaneLayout.setVerticalGroup(
            orderListControlButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, orderListControlButtonPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(orderListControlButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnRemoveMovie)
                    .addComponent(btnDeleteOrder)
                    .addComponent(btnAddMovie)
                    .addComponent(btnSaveExportCard))
                .addContainerGap())
        );

        orderListPaneNorth.add(orderListControlButtonPane, java.awt.BorderLayout.SOUTH);

        jScrollPane1.setViewportView(orderListPaneNorth);

        orderListSplitPane.setTopComponent(jScrollPane1);

        movieOrderListTabbedPane.addTab(bundle.getString("BussinessIFrame.movieOrderListScrollPane.TabConstraints.tabTitle"), movieOrderListScrollPane); // NOI18N

        jScrollPane3.setViewportView(movieOrderListTabbedPane);

        orderListSplitPane.setBottomComponent(jScrollPane3);

        orderListTabbedPane.addTab(bundle.getString("BussinessIFrame.orderListSplitPane.TabConstraints.tabTitle"), orderListSplitPane); // NOI18N

        getContentPane().add(orderListTabbedPane, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1067)/2, (screenSize.height-658)/2, 1067, 658);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteOrderActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this,
                "Xóa đơn hàng ?", "Xóa", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            dispatchChangOrderEvent(ChangeOrderEvent.DELETE_ORDER, getOrderTableSelectedIds());
        }
    }//GEN-LAST:event_btnDeleteOrderActionPerformed

    private void btnRemoveMovieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveMovieActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this,
                "Bỏ phim khỏi đơn hàng ?", "Bỏ phim", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            List params = new ArrayList(2);
            params.add(getOrderTableSelectedIds().get(0));
            params.add(getOrderItemTableSelectedIds());
            dispatchChangOrderEvent(ChangeOrderEvent.DELETE_ORDER_ITEM, params);
        }
    }//GEN-LAST:event_btnRemoveMovieActionPerformed

    private void btnAddMovieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMovieActionPerformed
        // TODO add your handling code here:        
        ChooseMovieDialog chooseMovieDialog = new ChooseMovieDialog(null);
        if (chooseMovieDialog.showDialog() == ChooseMovieDialog.DIALOG_OK) {
            List params = new ArrayList(2);
            params.add(getOrderTableSelectedIds().get(0));
            params.add(chooseMovieDialog.getSelectedMovieIds());
            dispatchChangOrderEvent(ChangeOrderEvent.ADD_ORDER_ITEM, params);
        }
    }//GEN-LAST:event_btnAddMovieActionPerformed

    private void btnSaveExportCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveExportCardActionPerformed
        // TODO add your handling code here:
        dispatchChangOrderEvent(ChangeOrderEvent.COMPLETE_ORDER, getOrderTableSelectedIds());
    }//GEN-LAST:event_btnSaveExportCardActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddMovie;
    private javax.swing.JButton btnDeleteOrder;
    private javax.swing.JButton btnRemoveMovie;
    private javax.swing.JButton btnSaveExportCard;
    private javax.swing.JScrollPane movieOrderListScrollPane;
    private javax.swing.JScrollPane orderListScrollPane;
    private javax.swing.JTabbedPane orderListTabbedPane;
    // End of variables declaration//GEN-END:variables

    private void prepareListenerRegistry() {
        addInternalFrameListener(bussinessIFrameListener);
    }
    // <editor-fold defaultstate="collapsed" desc="saveState-restoreState-setControlStatus">
    private List lastOrderTableSortKeys;
    private List lastOrderItemTableSortKeys;
    private List<Long> lastOrderTableSelectedIds;
    private List<Long> lastOrderItemTableSelectedIds;

    private void saveState() {
        lastOrderTableSortKeys = tblOrderItem.getRowSorter().getSortKeys();
        lastOrderTableSelectedIds = getOrderTableSelectedIds();
        lastOrderItemTableSortKeys = tblOrderItem.getRowSorter().getSortKeys();
        lastOrderItemTableSelectedIds = getOrderItemTableSelectedIds();
    }

    private void restoreState() {
        restoreTableState(tblOrder, lastOrderTableSortKeys, lastOrderTableSelectedIds);
        restoreTableState(tblOrderItem, lastOrderItemTableSortKeys, lastOrderItemTableSelectedIds);
    }

    private void restoreTableState(JTable tbl, List lastSortKeys, List<Long> lastSelectedIds) {
        tbl.setEnabled(true);
        tbl.clearSelection();

        tbl.getRowSorter().setSortKeys(lastSortKeys);
        for (Long id : lastSelectedIds) {
            for (int row = 0; row < tbl.getRowCount(); row++) {
                if (id.longValue() == ((Long) tbl.getValueAt(row, 0)).longValue()) {
                    tbl.addRowSelectionInterval(row, row);
                }
            }
        }
    }
    private static final int DISABLE_ALL = 0;
    private static final int DISABLE_CONTROL = 1;
    private static final int ENABLE_REMOVE_MOVIE = 2;
    private static final int DISABLE_REMOVE_MOVIE = 3;

    private void setControlStatus(int status) {
        switch (status) {
            case DISABLE_ALL:
                tblOrder.setEnabled(false);
                tblOrderItem.setEnabled(false);
                setEnableButton(false);
                break;
            case DISABLE_CONTROL:
                setEnableButton(false);
                break;
            case ENABLE_REMOVE_MOVIE:
                setEnableButton(true);
                break;
            case DISABLE_REMOVE_MOVIE:
                setEnableButton(true);
                btnRemoveMovie.setEnabled(false);
        }
    }

    private void setEnableButton(boolean enabled) {
        btnAddMovie.setEnabled(enabled);
        btnDeleteOrder.setEnabled(enabled);
        btnRemoveMovie.setEnabled(enabled);
        btnSaveExportCard.setEnabled(enabled);
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="prepareTable">
    private JTable tblOrder;
    private JTable tblOrderItem;
    private OrderTableModel orderTableModel;
    private OrderItemTableModel orderItemTableModel;

    private void prepareTable() {
        prepareOrderTable();
        prepareOrderItemTable();
    }

    private void prepareOrderTable() {
        tblOrder = new javax.swing.JTable();
        tblOrder.setAutoCreateRowSorter(true);
        tblOrder.setFillsViewportHeight(true);
        tblOrder.getTableHeader().setReorderingAllowed(false);
        orderListScrollPane.setViewportView(tblOrder);

        orderTableModel = new OrderTableModel();
        tblOrder.setModel(orderTableModel);

        tblOrder.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblOrder.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblOrder.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblOrder.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblOrder.getColumnModel().getColumn(4).setPreferredWidth(300);
        tblOrder.getColumnModel().getColumn(5).setPreferredWidth(200);
        tblOrder.getColumnModel().getColumn(6).setPreferredWidth(180);
        tblOrder.getColumnModel().getColumn(7).setPreferredWidth(70);
        tblOrder.setRowHeight(20);
        OrderTableCellRenderer cellRenderer = new OrderTableCellRenderer();
        tblOrder.setDefaultRenderer(Long.class, cellRenderer);
        tblOrder.setDefaultRenderer(String.class, cellRenderer);
        tblOrder.setDefaultRenderer(Date.class, cellRenderer);

        tblOrder.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblOrder.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                if (tblOrderItem.isEditing()) {
                    tblOrderItem.getCellEditor().cancelCellEditing();
                }
                int[] selectedRowIndex = tblOrder.getSelectedRows();
                switch (selectedRowIndex.length) {
                    case 0:
                        setControlStatus(DISABLE_CONTROL);
                        orderItemTableModel.setRowData(null);
                        break;
                    case 1:
                        int idx = tblOrder.convertRowIndexToModel(tblOrder.getSelectedRow());
                        OrderVO orderVO = orderTableModel.getOrderVO(idx);
                        orderItemTableModel.setRowData(orderVO.getOrderItemVOs());
                        setControlStatus(DISABLE_REMOVE_MOVIE);
                        break;
                    default:
                        orderItemTableModel.setRowData(null);
                        setControlStatus(DISABLE_REMOVE_MOVIE);
                }
            }
        });
        tblOrder.getRowSorter().addRowSorterListener(new RowSorterListener() {

            public void sorterChanged(RowSorterEvent e) {
                if (e.getType() == RowSorterEvent.Type.SORT_ORDER_CHANGED) {
                    lastOrderTableSortKeys = e.getSource().getSortKeys();
                }
            }
        });

        final DeliveryInfoEditor deliveryInfoEditor = new DeliveryInfoEditor();
        tblOrder.setDefaultEditor(String.class, deliveryInfoEditor);
        deliveryInfoEditor.addCellEditorListener(new CellEditorListener() {

            public void editingStopped(ChangeEvent e) {
                OrderVO orderVO = orderTableModel.getOrderVO(
                        tblOrder.convertRowIndexToModel(deliveryInfoEditor.getRow()));
                dispatchChangOrderEvent(ChangeOrderEvent.UPDATE_ORDER, orderVO);
            }

            public void editingCanceled(ChangeEvent e) {
            }
        });
    }

    private void prepareOrderItemTable() {
        tblOrderItem = new javax.swing.JTable();
        tblOrderItem.setAutoCreateRowSorter(true);
        tblOrderItem.setFillsViewportHeight(true);
        tblOrderItem.getTableHeader().setReorderingAllowed(false);
        movieOrderListScrollPane.setViewportView(tblOrderItem);

        orderItemTableModel = new OrderItemTableModel();
        tblOrderItem.setModel(orderItemTableModel);

        tblOrderItem.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblOrderItem.getColumnModel().getColumn(1).setPreferredWidth(800);
        tblOrderItem.getColumnModel().getColumn(2).setPreferredWidth(300);
        tblOrderItem.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblOrderItem.getColumnModel().getColumn(4).setPreferredWidth(200);
        tblOrderItem.getColumnModel().getColumn(5).setPreferredWidth(100);
        tblOrderItem.getColumnModel().getColumn(6).setPreferredWidth(200);
        tblOrderItem.setRowHeight(20);
        OrderItemTableCellRenderer cellRenderer = new OrderItemTableCellRenderer();
        tblOrderItem.setDefaultRenderer(Long.class, cellRenderer);
        tblOrderItem.setDefaultRenderer(String.class, cellRenderer);
        tblOrderItem.setDefaultRenderer(Double.class, cellRenderer);
        tblOrderItem.setDefaultRenderer(Integer.class, cellRenderer);

        tblOrderItem.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblOrderItem.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                int[] selectedRowIndex = tblOrderItem.getSelectedRows();
                switch (selectedRowIndex.length) {
                    case 0:
                        setControlStatus(DISABLE_REMOVE_MOVIE);
                        break;
                    default:
                        setControlStatus(ENABLE_REMOVE_MOVIE);
                }
            }
        });
        tblOrderItem.getRowSorter().addRowSorterListener(new RowSorterListener() {

            public void sorterChanged(RowSorterEvent e) {
                if (e.getType() == RowSorterEvent.Type.SORT_ORDER_CHANGED) {
                    lastOrderItemTableSortKeys = e.getSource().getSortKeys();
                }
            }
        });

        final OrderItemEditor orderItemEditor = new OrderItemEditor();
        tblOrderItem.setDefaultEditor(Double.class, orderItemEditor);
        tblOrderItem.setDefaultEditor(Integer.class, orderItemEditor);
        orderItemEditor.addCellEditorListener(new CellEditorListener() {

            public void editingStopped(ChangeEvent e) {
                OrderVO orderVO = orderTableModel.getOrderVO(
                        tblOrder.convertRowIndexToModel(tblOrder.getSelectedRow()));
                dispatchChangOrderEvent(ChangeOrderEvent.UPDATE_ORDER, orderVO);
            }

            public void editingCanceled(ChangeEvent e) {
            }
        });
    }// </editor-fold>  

    private List<Long> getOrderTableSelectedIds() {
        List<Long> selectedIds = new ArrayList<Long>();
        for (int idx : tblOrder.getSelectedRows()) {
            selectedIds.add((Long) tblOrder.getValueAt(idx, 0));
        }
        return selectedIds;
    }

    private List<Long> getOrderItemTableSelectedIds() {
        List<Long> selectedIds = new ArrayList<Long>();
        for (int idx : tblOrderItem.getSelectedRows()) {
            selectedIds.add((Long) tblOrderItem.getValueAt(idx, 0));
        }
        return selectedIds;
    }    
}
