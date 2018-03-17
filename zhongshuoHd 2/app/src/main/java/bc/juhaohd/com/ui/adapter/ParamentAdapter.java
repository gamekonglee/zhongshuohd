package bc.juhaohd.com.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bc.juhaohd.com.R;
import bc.juhaohd.com.bean.GoodsDetailBean;
import bc.juhaohd.com.bean.GoodsDetailZsBean;
import bc.juhaohd.com.cons.Constance;

/**
 * @author: Jun
 * @date : 2017/2/14 15:07
 * @description :
 */
public class ParamentAdapter extends BaseAdapter {
    private List<GoodsDetailZsBean.Attachments> mParamentLists;
    private Context mContext;

    public ParamentAdapter(List<GoodsDetailZsBean.Attachments> paramentLists, Context context) {
        mParamentLists = paramentLists;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (null == mParamentLists)
            return 0;
        return mParamentLists.size()>4?4:mParamentLists.size();
    }

    @Override
    public Object getItem(int position) {
        if (null == mParamentLists)
            return null;
        return mParamentLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_parameter_product, null);

            holder = new ViewHolder();
            holder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.value_tv = (TextView) convertView.findViewById(R.id.value_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name_tv.setText(mParamentLists.get(position).getName()+" ");
        if (mParamentLists.get(position).getAttrs().size()> 0) {
            holder.value_tv.setText(mParamentLists.get(position).getAttrs().get(0).getAttr_name());
        }
        return convertView;
    }

    class ViewHolder {
        TextView name_tv;
        TextView value_tv;

    }
}
