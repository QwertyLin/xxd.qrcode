package cn.xxd.qr;

import cn.xxd.qr.service.NewColorService;
import q.util.EventHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class NewColorF extends Fragment implements OnCheckedChangeListener {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_new_color, null);
		RadioGroup rg = (RadioGroup)view.findViewById(R.id.new_color);
		rg.check(R.id.new_color_black);
		rg.setOnCheckedChangeListener(this);
		return view;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton rb = (RadioButton)group.findViewById(checkedId);
		EventHelper.get().post(new NewColorService.ColorChange(rb.getTextColors().getDefaultColor()));
	}
	
	

}
