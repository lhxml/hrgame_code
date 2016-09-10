package com.hrgame.sdk.zgh;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.hrgame.lme.HRGGetParam;
import com.hrgame.lme.HRGJsonParser;
import com.testin.agent.TestinAgent;
import com.tw.OnLinePaySdk.EventCode;
import com.tw.OnLinePaySdk.PayKey;
import com.tw.OnLinePaySdk.TwOnlineSDK;
import com.tw.OnLinePaySdk.bean.RoleKeyCode;
import com.tw.OnLinePaySdk.callback.ChannelSpecialCallback;
import com.tw.OnLinePaySdk.callback.TWCallback;
import com.tw.OnLinePaySdk.tools.Tools;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class TwBridge extends UnityPlayerActivity {

	private static String GAME_OBJECT_NAME = "AndroidCallbackObject";
	private static Context context = null;

	// 参数配置文件，置于assets目录
	private static String file_name = "paramConf.xml";
	// 当前渠道名称
	private static String channel_name = "";
	// 当前渠道编号
	private static String channel_id = "";
	// 数据上报地址
	private static String send_url = "";
	// 渠道是否提供强制更新功能
	private static String is_update = "";
	// 拓维分配的AppId
	private static String tw_appid = "";
	
	@SuppressWarnings("finally")
	public String String2Json(int code, String data) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("code", code);
			obj.put("data", data);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} finally {
			return obj.toString();
		}

	}

	@SuppressWarnings("finally")
	public String Msg2Json(String msg) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("msg", msg);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} finally {
			return obj.toString();
		}
	}
	
	/* 
	 * 功能性接口，为提交用户信息接口传递参数

	 * @param serverId: 服务器Id，由游戏提供
	 * @param playerId：游戏角色Id，由游戏提供
	 * @param playerName: 游戏角色名称，由游戏提供
	 * @param playerLevel: 玩家等级，由游戏提供

	 * @return：返回Intent对象
	 */
	private Intent getRoleMsgIntent (final int serverId, final String serverNumber, final String serverName, final String playerId, final String playerName, final int playerLevel, final int roleCreateTime, final int rolePowerModifyTime)
	{
		Log.d("userinfo", "serverId is " + serverId + 
				" ::: serverNumber is " + serverNumber + 
				" ::: serverName is " + serverName + 
				" ::: playerId is " + playerId + 
				" ::: playerName is " + playerName + 
				" ::: playerLevel is " + playerLevel +
				" ::: roleCreateTime is " + roleCreateTime +
				" ::: rolePowerModifyTime is " + rolePowerModifyTime);

		Bundle bundle = new Bundle();
		// 服务器Id
		bundle.putString(RoleKeyCode.ServerId, String.valueOf(serverId));
		// 游戏角色Id
		bundle.putString(RoleKeyCode.RoleId, playerId);
		// 游戏角色名称
		bundle.putString(RoleKeyCode.RoleName, playerName);
		// 游戏角色等级
		bundle.putString(RoleKeyCode.RoleLevel, String.valueOf(playerLevel));
		// 服务器名称
		bundle.putString(RoleKeyCode.ServerName, serverNumber + " " + serverName);
		// 游戏角色VIP等级（没有填“0”）
		bundle.putString(RoleKeyCode.VipLevel, "0");
		// 游戏角色账户余额（没有填“0”）
		bundle.putString(RoleKeyCode.Balance, "0");
		// 游戏角色所在公会Id（没有公会填“none”）
		bundle.putString(RoleKeyCode.PartyId, "none");
		// 游戏角色所在公会名称（没有公会填“none”）
		bundle.putString(RoleKeyCode.PartyName, "none");
		// 游戏角色创建时间
		bundle.putString(RoleKeyCode.RoleCreateTime, String.valueOf(roleCreateTime));
		// 游戏角色战力变化时间
		bundle.putString(RoleKeyCode.RoleLevelTime, String.valueOf(rolePowerModifyTime));

		Intent intent = new Intent();
		intent.putExtras(bundle);

		return intent;
	}
	
	private Intent getRechargeIntent (final String goodId, final String orderSerial, final String gameBak)
	{
		Log.d("pay", "goodsId is " + goodId + "::: orderNumber is " + orderSerial);

		Bundle bundle = new Bundle();
		bundle.putString(PayKey.GoodId, goodId);
		bundle.putString(PayKey.OrderSerial, orderSerial);
		bundle.putString(PayKey.GameBak, gameBak);

		Intent intent = new Intent(this, TwBridge.class);
		intent.putExtras(bundle);

		return intent;
	}
	
	private TWCallback twCallback = new TWCallback()
    {
    	public void responseData(int payEvent, String message)
    	{
			switch (payEvent) {
			
			case EventCode.INIT_EVENT_CODE:
				if (message!=null&&!message.equals(""))
				{
					Log.d("init", payEvent + ":::" + message);

					HashMap<String, String> initMap = (HashMap<String, String>) HRGJsonParser.toMap(message);
					String tag = initMap.get("code");

					if (tag.equals("01"))
					{
						// 初始化成功，修改标志位
						// isInited = true;

						String ret = String2Json(0, Msg2Json("Init succeeded!"));
						sendMessageToUnity("OnSdkInitResult", ret);

						Log.d("init", "Send msg to Unity from initCallback");
					}
				}
				else {
					Tools.showToast(context, "Verify failed!");
				}
				
				break;

			// 检查更新
			case EventCode.CHECKCERSION_EVENT_CODE:
				if (message!=null&&!message.equals(""))
				{
					Log.d("update", payEvent + ":::" + message);

					HashMap<String, String> initMap = (HashMap<String, String>) HRGJsonParser.toMap(message);
					String tag = initMap.get("code");
				}
				else {
					Tools.showToast(context, "Verify failed!");
				}
				
				break;

			case EventCode.LOGIN_EVENT_CODE:
				if (message!=null&&!message.equals(""))
				{
					Log.d("login", payEvent + ":::" + message);

					HashMap<String, String> loginMap = (HashMap<String, String>) HRGJsonParser.toMap(message);
					String tag = loginMap.get("code");
					String channelId = loginMap.get("channelId");
					String sessionId = loginMap.get("sessionId");
					String userId = loginMap.get("userId");
					String userName = loginMap.get("userName");
					String extension = channelId + "|" + userName;
					
					if (tag.equals("01"))
					{
						JSONObject obj = new JSONObject();
						try {
							obj.put("uid", userId);
							obj.put("sid", sessionId);
							obj.put("ext", extension);
							obj.put("msg", "Login succeeded!");
							String call_ret = String2Json(0, obj.toString());
							sendMessageToUnity("OnSdkLoginResult", call_ret);
						}
						catch (JSONException e) {
							e.printStackTrace();
						}
					}
					else if (tag.equals("02"))
					{
						// 登陆错误（失败），部分渠道退出登陆界面的回调值也为“02”
						JSONObject obj = new JSONObject();
						try {
							obj.put("uid", userId);
							obj.put("sid", sessionId);
							obj.put("ext", extension);
							obj.put("msg", "Login failed(cancelled)!");
							String call_ret = String2Json(1, obj.toString());
							sendMessageToUnity("OnSdkLoginResult", call_ret);
						}
						catch (JSONException e) {
							e.printStackTrace();
						}
					}
					else if (tag.equals("03"))
					{
						JSONObject obj = new JSONObject();
						try {
							obj.put("uid", userId);
							obj.put("sid", sessionId);
							obj.put("ext", extension);
							obj.put("msg", "Network failure!");
							String call_ret = String2Json(-1, obj.toString());
							sendMessageToUnity("OnSdkLoginResult", call_ret);
						}
						catch (JSONException e) {
							e.printStackTrace();
						}
					}
					else if (tag.equals("04"))
					{
						JSONObject obj = new JSONObject();
						try {
							obj.put("uid", userId);
							obj.put("sid", sessionId);
							obj.put("ext", extension);
							obj.put("msg", "Unknown error!");
							String call_ret = String2Json(1, obj.toString());
							sendMessageToUnity("OnSdkLoginResult", call_ret);
						}
						catch (JSONException e) {
							e.printStackTrace();
						}
					}
					else if (tag.equals("05"))
					{
						// 登陆取消，退出登录界面归为此类，需要特殊处理
						JSONObject obj = new JSONObject();
						try {
							obj.put("uid", userId);
							obj.put("sid", sessionId);
							obj.put("ext", extension);
							obj.put("msg", "Try again!");
							String call_ret = String2Json(1, obj.toString());
							sendMessageToUnity("OnSdkLoginResult", call_ret);
						}
						catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
				else {
					Tools.showToast(context, "Verify failed!");
				}
				
				break;
				
			case EventCode.PAY_EVENT_CODE:
				if (message!=null&&!message.equals(""))
				{
					Log.d("pay", payEvent + ":::" + message);

					HashMap<String, String> payMap = (HashMap<String, String>) HRGJsonParser.toMap(message);
					String resultcode = payMap.get("code");
					String resultorderId = payMap.get("orderId");

					if (resultcode.equals("01"))
					{
						String ret = String2Json(0, Msg2Json("Order succeeded!"));
						sendMessageToUnity("OnSdkOrderResult", ret);
					}

					else if (resultcode.equals("02"))
					{
						String ret = String2Json(-1, Msg2Json("Order failed!"));
						sendMessageToUnity("OnSdkOrderResult", ret);
					}

					else if (resultcode.equals("03"))
					{
						String ret = String2Json(-1, Msg2Json("Network failure!"));
						sendMessageToUnity("OnSdkOrderResult", ret);
					}

					else if (resultcode.equals("04"))
					{
						String ret = String2Json(-1, Msg2Json("Ordering!"));
						sendMessageToUnity("OnSdkOrderResult", ret);
					}

					else if (resultcode.equals("05"))
					{
						String ret = String2Json(-1, Msg2Json("Order cancelled!"));
						sendMessageToUnity("OnSdkOrderResult", ret);
					}
				}
				else {
					Tools.showToast(context, "Verify failed!");
				}
				
				break;

			// 打开论坛
			case EventCode.OPEN_BBS_EVENT_CODE:
				if (message!=null&&!message.equals(""))
				{
					Log.d("bbs", payEvent + ":::" + message);

					HashMap<String, String> initMap = (HashMap<String, String>) HRGJsonParser.toMap(message);
					String tag = initMap.get("code");
				}
				else {
					Tools.showToast(context, "Verify failed!");
				}
				
				break;

			// 打开用户中心
			case EventCode.OPEN_USERCENTER_EVENT_CODE:
				if (message!=null&&!message.equals(""))
				{
					Log.d("userCenter", payEvent + ":::" + message);

					HashMap<String, String> initMap = (HashMap<String, String>) HRGJsonParser.toMap(message);
					String tag = initMap.get("code");
				}
				else {
					Tools.showToast(context, "Verify failed!");
				}
				
				break;
				
			// 注销账号
			case EventCode.LOGOUT_ACCOUNT_EVENT_CODE:
				if (message!=null&&!message.equals(""))
				{
					Log.d("logout", payEvent + ":::" + message);

					HashMap<String, String> logoutMap =(HashMap<String, String>) HRGJsonParser.toMap(message);
					String tag = logoutMap.get("code");

					if (tag.equals("01"))
					{
						String ret = String2Json(0, Msg2Json("Logout succeeded!"));
						sendMessageToUnity("OnSdkLogout", ret);
						SdkLogin();
					}
				}
				else {
					Tools.showToast(context, "Verify failed!");
				}
				
				break;
				
			// 退出SDK的各项功能
			case EventCode.DESTORY_SDK_EVENT_CODE:
				if (message!=null&&!message.equals(""))
				{
					Log.d("exit", payEvent + ":::" + message);

					HashMap<String, String> exitMap =(HashMap<String, String>) HRGJsonParser.toMap(message);
					String tag = exitMap.get("code");

					if (tag.equals("01"))
					{
						String ret = String2Json(0, Msg2Json("Exit succeeded!"));
						
						// 只有在渠道存在退出界面时才返回回调，否则使用游戏自带的退出界面
						if (TwOnlineSDK.existDestroySDKWindow())
						{
							sendMessageToUnity("OnSdkExit", ret);
						}
					}
				}
				else {
					Tools.showToast(context, "Verify failed!");
				}
				
				break;

			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = UnityPlayer.currentActivity;
        
    	/******************************************* 从配置文件paramConf.xml读取相关参数 **********************************************/
    	// 当前渠道名称
        channel_name = HRGGetParam.getValueFromAssetsByParamName(context, file_name, "channelName");
    	// 当前渠道编号
        channel_id = HRGGetParam.getValueFromAssetsByParamName(context, file_name, "channelId");
    	// 数据上报地址
        send_url = HRGGetParam.getValueFromAssetsByParamName(context, file_name, "sendUrl");
        // 渠道是否提供强制更新功能
        is_update = HRGGetParam.getValueFromAssetsByParamName(context, file_name, "isUpdate");
        // 拓维分配的AppId
        tw_appid = HRGGetParam.getValueFromAssetsByParamName(context, file_name, "twAppId");
    	/*********************************************************************************************************************/
        TestinAgent.init(this,"ae940b4ae0db3c1651b16b67d7857516", channel_name);
        Log.d("TwBridge", "testin inited.");
        
		TwOnlineSDK.onCreate(context);
	}

	public void SdkInit() {
		new Handler (Looper.getMainLooper()).post (new Runnable ()
    	{
    		public void run ()
    		{
				// 龙门镖局的拓维appid为61
				// 横屏为true，竖屏为false
				TwOnlineSDK.initSDK(TwBridge.this, tw_appid, true, twCallback, new ChannelSpecialCallback()
				{
					public void logoutAccount(int eventCode, String resultData)
    				{
						Log.d("logoutInUserCenter", eventCode + ":::" + resultData);

						HashMap<String, String> logoutMap =(HashMap<String, String>) HRGJsonParser.toMap(resultData);
						String tag = logoutMap.get("code");

						if (tag.equals("01"))
						{
							String ret = String2Json(0, Msg2Json("Logout succeeded!"));
							sendMessageToUnity("OnSdkLogout", ret);
						}
					}

					public void switchAccount(int eventCode, String resultData)
					{
						Log.d("loginInUserCenter", eventCode + ":::" + resultData);

						HashMap<String, String> logoutMap =(HashMap<String, String>) HRGJsonParser.toMap(resultData);
						String tag = logoutMap.get("code");

						if (tag.equals("01"))
						{
							String ret = String2Json(0, Msg2Json("Logout succeeded!"));
							sendMessageToUnity("OnSdkLogout", ret);
						}
					}

					public void otherHandler(int eventCode, String resultData)
					{
						Log.d("otherHandler", eventCode + ":::" + resultData);

						// TODO
					}
				});
    		}
    	});
	}

	public void SdkEnterGame(String serverId, String playerId, String playerName) {
		
	}
	
	public void SubmitPlayerInfo(int serverId, String playerId, String playerName, String serverName, int powerValue, int roleCreateTime, int rolePowerModifyTime)
	{
		// 调用某些功能前提交用户信息，越来越多的渠道有此要求
				// 注意，数据上报时不要乱开UI线程
				TwOnlineSDK.sendUserInfo(context, getRoleMsgIntent(serverId, "", serverName, playerId, playerName, powerValue, roleCreateTime, rolePowerModifyTime), twCallback);
	}

	public void SdkLogin() {
		new Handler (Looper.getMainLooper ()).post (new Runnable ()
		{
			public void run ()
			{
				TwOnlineSDK.loginSDK(context, twCallback);
			}
		});
	}

	public void SdkOrderGoods(final String orderNumber, final double priceInPlatformCurrency, final String goodsId, int goodsIndex, final String goodsName, final String playerId) {
		new Handler (Looper.getMainLooper ()).post (new Runnable ()
		{
			public void run ()
			{
				Log.d("pay", "For orderNumber " + orderNumber + ", goodsId is " + goodsId + ", bought by " + playerId);

		    	TwOnlineSDK.pay(context, getRechargeIntent(goodsId, orderNumber, "CN-ZGH"), twCallback);
			}
		});
	}

	public void SdkShowOrHideFloatingIcon(boolean visible) {
		if (TwOnlineSDK.existFloatingWindow())
    	{
			if (visible)
			{
				new Handler (Looper.getMainLooper ()).post (new Runnable ()
				{
					public void run ()
					{
						TwOnlineSDK.openFloatingWindow(context, twCallback);
					}
				});
			}
    	}
	}
	
	public boolean SdkHasPlayerCenter() {
		return (TwOnlineSDK.existUserCenter());
	}
	
	public void SdkShowPlayerCenter() {
		if (TwOnlineSDK.existUserCenter())
    	{
    		new Handler (Looper.getMainLooper()).post (new Runnable ()
    		{
    			public void run ()
    			{
					TwOnlineSDK.openUserCenter(context, twCallback);
    			}
    		});
    	}
	}
	
	public boolean SdkHasForum() {
		return (TwOnlineSDK.existBbs());
	}

	public void SdkShowForum() {
		if (TwOnlineSDK.existBbs())
    	{
    		new Handler (Looper.getMainLooper()).post (new Runnable ()
    		{
    			public void run ()
    			{
					TwOnlineSDK.openBbs(context, twCallback);
    			}
    		});
    	}
	}
	
	public boolean HasLogout() {
		return (TwOnlineSDK.existLogoutAccount());
	}

	public void SdkLogout() {
		if (TwOnlineSDK.existLogoutAccount())
    	{
    		new Handler (Looper.getMainLooper ()).post (new Runnable ()
    		{
    			public void run ()
    			{
    				if (TwOnlineSDK.existLogoutAccount())
    				{
    					TwOnlineSDK.logoutAccount(context, twCallback);
    				}
    			}
    		});
    	}
	}
	
	public boolean SdkHasLogoutInPlayerCenter() {
		return false;
	}
	
	public boolean SdkHasExit() {
		return (TwOnlineSDK.existDestroySDKWindow());
	}

	public void SdkExit() {
		if (TwOnlineSDK.existDestroySDKWindow())
    	{
    		new Handler (Looper.getMainLooper ()).post (new Runnable ()
    		{
    			public void run ()
    			{
    				// 安卓系统存在退出程序这一概念，该接口是用于退出游戏的！
    				TwOnlineSDK.destroySDK(context, twCallback);
    			}
    		});
    	}
	}

	private void sendMessageToUnity(String bindMethod, String message) {
		UnityPlayer.UnitySendMessage(GAME_OBJECT_NAME, bindMethod, message);
	}

	public int SdkGetChannelType() {
		return Integer.parseInt(channel_id);
	}

	public int SdkGetSdkType() {
		return Integer.parseInt(channel_id);
	}

	public boolean ShouldUseDefaultUpgradingDialog() {
		return Boolean.parseBoolean(is_update);
	}
	
	public void SdkResumeGame() {

	}
	
	@Override
	protected void onStart() {
		super.onStart();
		TwOnlineSDK.onStart(context);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		TwOnlineSDK.onRestart(context);
	}

	@Override
	protected void onPause() {
		super.onPause();
		TwOnlineSDK.onPause(context);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		TwOnlineSDK.onResume(context);
	}

	@Override
	protected void onStop() {
		super.onStop();
		TwOnlineSDK.onStop(context);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		TwOnlineSDK.onDestroy(context);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		TwOnlineSDK.onNewIntent(context, intent);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		TwOnlineSDK.onActivityResult(context, requestCode, resultCode, data);
	}
}
