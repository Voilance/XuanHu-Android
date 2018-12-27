package cn.biketomotor.xh.xuanhu.Interface;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Adapter.HistoryCourseCommentItemAdapter;
import cn.biketomotor.xh.xuanhu.Item.CommentItem;

public interface AddCommentDialogPopupable {
    public abstract void popupAddCommentDialog(final HistoryCourseCommentItemAdapter adapter, final List<CommentItem> comments, final CommentItem parent);
}
