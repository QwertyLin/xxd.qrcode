package q.out;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.widget.Toast;

public class UpdateUtil {
	
	private static final String UPDATE_URL = "http://www.xxd.cn/update.php?v=";
	public static void check(final Context ctx, final boolean visible){
		final ProgressDialog pd = new ProgressDialog(ctx);
		if(visible){
			pd.setMessage("正在检查更新...");
			pd.show();
		}
		new Thread(){
			public void run() {
				try {
					ApplicationInfo info = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(),  PackageManager.GET_META_DATA);
					String channel = String.valueOf(info.metaData.get("BaiduMobAd_CHANNEL"));
					//
					String url = UPDATE_URL + ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode + "&c=" + channel;
					final String result = QHttpUtil.get(url);
					//System.out.println(url);
					//System.out.println(result);
					if(visible){
						pd.cancel();
					}
					if(result != null && result.startsWith("http")){
						((Activity)ctx).runOnUiThread(new Runnable() {
							@Override
							public void run() {
								final String[] rs = result.split("-xxd-");
								if(rs.length < 2){
									return;
								}
								new AlertDialog.Builder(ctx)
								.setMessage(rs[1])
								.setPositiveButton("更新", new DialogInterface.OnClickListener(){
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(rs[0])).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
									}
								})
								.setNegativeButton("取消", null)
								.show();
							}
						});
					}else{
						if(visible){
							((Activity)ctx).runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(ctx, "无检测到更新", Toast.LENGTH_SHORT).show();
								}
							});
						}
					}
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

}
