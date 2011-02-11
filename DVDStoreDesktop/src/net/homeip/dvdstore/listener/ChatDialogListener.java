/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener;

// <editor-fold defaultstate="collapsed" desc="import">
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.SwingWorker;
import net.homeip.dvdstore.delegate.ChatServiceDelegate;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.dialog.ChatDialog;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.ChatVO;
import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.webservice.exception.DuplicateException;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class ChatDialogListener extends WindowAdapter
        implements DataChangingListener, DataChangedListener {

    private ChatServiceDelegate chatServiceDelegate;

    @Override
    public void windowOpened(WindowEvent e) {
        LoadDataModel<List<ChatVO>> chatListModel =
                ((ChatDialog) e.getSource()).getChatListModel();
        callLoadService(chatListModel);
    }

    public void onDataChanged(DataChangedEvent evt) {
        LoadDataModel<List<ChatVO>> chatListModel =
                ((ChatDialog) evt.getSource()).getChatListModel();
        callLoadService(chatListModel);
    }

    public void onDataChanging(DataChangingEvent evt) {
        DataChangingModel dataChangingModel = evt.getDataChangingModel();
        int action = evt.getAction();
        Object param = evt.getParam();

        if ((action == DataChangingEvent.ADD || action == DataChangingEvent.MODIFY)
                && validate((ChatVO) param)) {
            dataChangingModel.addInvalidProperty("chatName");
            dataChangingModel.setStatus(DataChangingModel.VALIDATE_FAIL);
            return;
        }
        if ((action == DataChangingEvent.DELETE && validate((List<Long>) param))) {
            dataChangingModel.addInvalidProperty("chatId");
            dataChangingModel.setStatus(DataChangingModel.VALIDATE_FAIL);
            return;
        }

        switch (action) {
            case DataChangingEvent.ADD:
                callAddService(dataChangingModel, (ChatVO) param);
                break;
            case DataChangingEvent.DELETE:
                callDeleteService(dataChangingModel, (List<Long>) param);
                break;
            case DataChangingEvent.MODIFY:
                callModifyService(dataChangingModel, (ChatVO) param);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="callLoadService">
    private void callLoadService(final LoadDataModel<List<ChatVO>> movCatListModel) {
        if (chatServiceDelegate == null) {
            throw new IllegalStateException("chatServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    movCatListModel.setData(chatServiceDelegate.getChatList());
                } catch (ServerConnectionException ex) {
                    ex.printStackTrace();
                    return LoadDataModel.SERVER_CONNECTION_ERROR;
                } catch (ServerException ex) {
                    ex.printStackTrace();
                    return LoadDataModel.SERVER_ERROR;
                }
                return LoadDataModel.OK;
            }

            @Override
            protected void done() {
                try {
                    int status = get();
                    movCatListModel.setStatus(status);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="callAddService">
    private void callAddService(final DataChangingModel dataChangingModel,
            final ChatVO chatVO) {
        if (chatServiceDelegate == null) {
            throw new IllegalStateException("chatServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    chatServiceDelegate.addChat(chatVO);
                } catch (InvalidInputException ex) {
                    dataChangingModel.addInvalidProperty(ex.getMessage());
                    return DataChangingModel.VALIDATE_FAIL;
                } catch (DuplicateException ex) {
                    dataChangingModel.setDuplicateProperty(ex.getMessage());
                    return DataChangingModel.DUPLICATE;
                } catch (ServerConnectionException ex) {
                    return DataChangingModel.SERVER_CONNECTION_ERROR;
                } catch (ServerException ex) {
                    return DataChangingModel.SERVER_ERROR;
                }
                return DataChangingModel.OK;
            }

            @Override
            protected void done() {
                try {
                    dataChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="callDeleteService">
    private void callDeleteService(final DataChangingModel dataChangingModel,
            final List<Long> chatIdList) {
        if (chatServiceDelegate == null) {
            throw new IllegalStateException("chatServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    chatServiceDelegate.deleteChat(chatIdList);
                } catch (ServerConnectionException ex) {
                    return DataChangingModel.SERVER_CONNECTION_ERROR;
                } catch (ServerException ex) {
                    return DataChangingModel.SERVER_ERROR;
                }
                return DataChangingModel.OK;
            }

            @Override
            protected void done() {
                try {
                    dataChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="callModifyService">
    private void callModifyService(final DataChangingModel dataChangingModel,
            final ChatVO chatVO) {
        if (chatServiceDelegate == null) {
            throw new IllegalStateException("chatServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    chatServiceDelegate.updateChat(chatVO);
                } catch (InvalidInputException ex) {
                    dataChangingModel.addInvalidProperty(ex.getMessage());
                    return DataChangingModel.VALIDATE_FAIL;
                } catch (DuplicateException ex) {
                    dataChangingModel.setDuplicateProperty(ex.getMessage());
                    return DataChangingModel.DUPLICATE;
                } catch (ServerConnectionException ex) {
                    return DataChangingModel.SERVER_CONNECTION_ERROR;
                } catch (ServerException ex) {
                    return DataChangingModel.SERVER_ERROR;
                }
                return DataChangingModel.OK;
            }

            @Override
            protected void done() {
                try {
                    dataChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    public void setChatServiceDelegate(ChatServiceDelegate chatServiceDelegate) {
        this.chatServiceDelegate = chatServiceDelegate;
    }

    private boolean validate(List<Long> list) {
        if (list.size() == 0) {
            return true;
        }
        return false;
    }

    private boolean validate(ChatVO chatVO) {
        String chatName = chatVO.getChatName();
        if (ValidatorUtil.isEmpty(chatName)
                || ValidatorUtil.isInvalidLength(chatName, 1, 20)) {
            return true;
        }
        return false;
    }
}