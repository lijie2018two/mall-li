package com.ruoyi.common.core.constant;

/**
 * 常量类型
 *
 * @author hyyt
 *
 */
public class ApplicationConst {
	
	public static final String APP_NAME = "游艇管家";
	//生成行程的天数
	public static final int DEFAULT_DATE_COUNT = 30;
	//一天的时间戳（毫秒）
	public static final long ONE_DAY = 24 * 3600 * 1000;

    public static final long BAN_DAY = 12 * 3600 * 1000;
	
	//商品提前5分钟关闭（毫秒）
	public static final long FIVE_MINUTE = 5 * 60 * 1000;
	
	//订单倒计时3分钟（毫秒）
	public static final long THREE_MINUTE = 3 * 60 * 1000;
	
	//测试用10毫秒
//	public static final long THREE_MINUTE = 10 * 1000;
	
	public static final int ALPAY = 1;
	public static final int WXPAY = 2;
	
	//订单说明（公司）
	public static final String ORDER_BODY = "三亚后元游艇服务有限公司";
	
	//提现标题
	public static final String COMMISSION_BODY = "游艇管家代发";
	
	//邀请码前缀
	public static final String INVITATION_CODE_PREFIX = "YTGJ";
	
	//系统操作者
	public static final String SYSTEM_OPERATION = "系统核销";

	/**	 ページ番号 初期値 */
	public static final int DEFAULT_PAGE = 1;
	/**管理员	 */
	public static final int MANAGER = 1;

	/** 1ページの表示件数 初期値 */
	public static final int DEFAULT_SIZE = 10;

	/** 	ソート順 降順 */
	public static final String ORDER_DESC = "descending";

	/** 	ソート順 昇順 */
	public static final String ORDER_ASC = "ascending";
	
	public static final String MORE_PIC_TAG = "&&";
	
	public static final String SETTING_TAG = ",";
	
	/** 	会员名 */
	public static final String SPECIAL_NAME = "海马会员";
	
	/** 	保险说明 */
	public static final String INSURANCE_DESCRIPTION = "船艇已购公众责任险，如需可在下方自行购买其他保险";

    /** CSV_ENCODING */
//	public static final String CSV_ENCODING = "Windows-31J";
	public static final String CSV_ENCODING = "Shift_JIS";
	public static final String HTTP_HEADER_ENCODING = "UTF-8";//"Shift_JIS";

	public static final String[] VERI_HEADERNAMES = {"データ管理区分", "キーワード１", "キーワード2", "問合せ結果"};
	public static final String[] T_UNMATCHANLS_HEADERNAMES = {"元文字","異文字","入力件数","アンマッチ件数","発生率","文字優先区分","作成者","作成日時","更新者","更新日時"};

    public static final String[] VERI_DETAILS = {"DATAKBN","KEYWORD1", "KEYWORD2", "RESULT"};

    public static final String[] BATCHCOMPARE_DETAILS = {"DATAKBN", "BERIFY_RSLT", "ENTRY_RSLT"};

//    /** 	管理者の権限 */
//	public static final String AUTDIV_ADMIN = "01";
//
//	/** 	利用者の権限 */
//    public static final String AUTDIV_USER = "02";
    
    public static final int XIAOSHU_LENGHT = 0;
    
    //支付宝服务响应成功返回码
    public static final String AL_RESPONSE_SUCCESS_CODE = "10000";
    
    //转账余额不足
    public static final String AL_RESPONSE_ERROR_40004 = "40004";

    //	0:管理者
	public static final String AUTDIV_ADMIN = "0";
    //	1:登録者
	public static final String AUTDIV_USER = "1";
    //	2:参照者
	public static final String AUTDIV_NORMAL = "2";

	public static final int CREATE = 0;
	public static final int UPDATE = 1;

    /** 	同一パスワード設定可否 1:否  null:可 */
    public static final String SAMEPWDFLG_ON = "1";

    /** 	初期管理者ID:00001 */
    public static final String ADMNUSRID = "00001";
    /**	データ管理区分 DEFAULT_CODEKBN     */
    public static final String DEFAULT_CODEKBN = "01";

    /**	ダウンロードファイルのプレフィックス		*/
    public static final String FILE_PREFIX = "veri";

    /** CSV ファイルの拡張子 		*/
    public static final String CSV_SUFFIX = ".csv";

    /** LOG ファイルの拡張子 		*/
    public static final String LOG_SUFFIX = ".log";

    /** ZIP ファイルの拡張子 		*/
    public static final String ZIP_SUFFIX = ".zip";

    /** CSV	ダウンロードパス*/
    public static final String CSV_PATH = "CSV";
    
    /** 图片存放场所*/
    public static final String IMAGE_PATH = "IMAGE";

    /** LOG	ダウンロードパス*/
	public static final String ERRORLOG_PATH = "ERRORLOG";

	/** 	バッチファイルダウンロードパス*/
	public static final String BATCHFILE_PATH = "BATCHFILE";

	/**	アップロードされたファイルのサイズ*/
	public static final int CSV_SIZE_MAX = 1024*1024*20;

    /**	間違い情報データベース登録 ERROR MESSAGES    */
	public static final String ERRORMSG_00101 = "00101";//	データ管理区分(DATAKBN)が設定されていません。
    public static final String ERRORMSG_00102 = "00102";//	データ管理区分(DATAKBN)が最大文字数を超えました。
    public static final String ERRORMSG_00103 = "00103";//	データ管理区分(DATAKBN)が存在しません。
    public static final String ERRORMSG_00104 = "00104";//	エントリー値(ENTRY_RSLT)が設定されていません。
    public static final String ERRORMSG_00105 = "00105";//	エントリー値(ENTRY_RSLT)が最大文字数を超えました。
    public static final String ERRORMSG_00106 = "00106";//	ベリ値(BERIFY_RSLT)が最大文字数を超えました。
    public static final String ERRORMSG_00107 = "00107";//	入力日(CHAR_DATE)が設定されていません。
    public static final String ERRORMSG_00108 = "00108";//	入力日(CHAR_DATE)が最大文字数を超えました。
    public static final String ERRORMSG_00109 = "00109";//	入力日(CHAR_DATE)が正しい日付ではありません。
    public static final String ERRORMSG_10401 = "10401";//  请指定导入文件。
    public static final String ERRORMSG_10402 = "10402";//	ファイルフォーマットは正しくありません。
    public static final String ERRORMSG_10403 = "10403";//	{0}は空です。
    public static final String ERRORMSG_10404 = "10404";//	{0}が最大文字数を超えました。
    public static final String ERRORMSG_10405 = "10405";//	{0}はデータ形式不正です。
    public static final String ERRORMSG_10406 = "10406";//	ファイルが解凍できません。
    public static final String ERRORMSG_10407 = "10407";//	該当オペレーターユーザー｛0｝がいません。
    public static final String ERRORMSG_10408 = "10408";//	別途変換用Csvファイルがいません。
    public static final String ERRORMSG_10409 = "10409";//	行目の項目数が不正です。

    /**	#ベリファイ問合せ ERROR MESSAGES    */
    public static final String ERRORMSG_10501 = "10501";//	問合せファイルを指定してください。
    public static final String ERRORMSG_10502 = "10502";//	ファイルフォーマットは正しくありません。
    public static final String ERRORMSG_10503 = "10503";//	キーワード１とキーワード２のいずれかの項目を指定してください。
    public static final String ERRORMSG_10504 = "10504";//	{0}が最大文字数を超えました。
    public static final String ERRORMSG_10505 = "10505";//	ファイルが解凍できません。
    public static final String ERRORMSG_10506 = "10506";//	{0}を入力してください。
    public static final String ERRORMSG_10509 = "10509";//	行目の項目数が不正です。

    /**	WEBAPI　：　APIサービス認証 エラーコード*/
    public static final String ERRORMSG_00001 ="00001"; //	服务出现异常
    public static final String ERRORMSG_00002 ="00002"; //	APIサービスの認証は失敗しました。

	/**	WEBAPI　：　InqVerifyAssist　ベリファイアシスト情報取得 AQPN: Asist Query Project Name	項目ID */
    public static final String AQPN_IDX = "IDX";	//	識別子
    public static final String AQPN_DATAKBN = "DATAKBN";	//	データ管理区分
    public static final String AQPN_KEYWORD1 = "KEYWORD1";	//	キーワード１
    public static final String AQPN_KEYWORD2 = "KEYWORD2";	//	キーワード２
    public static final String AQPN_ASSISTCNT = "ASSISTCNT";	//	アシスト取得件数

    /**	#ベリファイアシスト情報取得 ERROR MESSAGES    */
    public static final String ERRORMSG_00201 = "00201";//	識別子(IDX)が設定されていません。
    public static final String ERRORMSG_00202 = "00202";//	識別子(IDX)が最大文字数を超えました。
    public static final String ERRORMSG_00203 = "00203";//	データ管理区分(DATAKBN)が設定されていません。
    public static final String ERRORMSG_00204 = "00204";//	データ管理区分(DATAKBN)が存在しません。
    public static final String ERRORMSG_00205 = "00205";//	キーワード１(KEYWORD1)が設定されていません。
    public static final String ERRORMSG_00206 = "00206";//	キーワード１(KEYWORD1)が最大文字数を超えました。

    /**	#入力集計情報登録 ERROR MESSAGES    */
    public static final String ERRORMSG_00301 = "00301";//	入力日(CHAR_DATE)が設定されていません。
    public static final String ERRORMSG_00302 = "00302";//	入力日(CHAR_DATE)が最大文字数を超えました。
    public static final String ERRORMSG_00303 = "00303";//	入力日(CHAR_DATE)が正しい日付ではありません。
    public static final String ERRORMSG_00304 = "00304";//	入力文字(CHAR_VALUE)が設定されていません。
    public static final String ERRORMSG_00305 = "00305";//	入力文字(CHAR_VALUE)が最大文字数を超えました。
    public static final String ERRORMSG_00306 = "00306";//	入力件数(CHAR_COUNT)が設定されていません。
    public static final String ERRORMSG_00307 = "00307";//	入力件数(CHAR_COUNT)が正しい数値ではありません。

    /**	#正しい入力結果登録 ERROR MESSAGES    */
    public static final String ERRORMSG_00501 = "00501";//	入力日(CHAR_DATE)が設定されていません。
    public static final String ERRORMSG_00502 = "00502";//	入力日(CHAR_DATE)が最大文字数を超えました。
    public static final String ERRORMSG_00503 = "00503";//	入力日(CHAR_DATE)が正しい日付ではありません。
    public static final String ERRORMSG_00504 = "00504";//	データ管理区分(DATAKBN)が設定されていません。
    public static final String ERRORMSG_00505 = "00505";//	データ管理区分(DATAKBN)が最大文字数を超えました。
    public static final String ERRORMSG_00507 = "00507";//	入力文字(CHAR_VALUE)が設定されていません。
    public static final String ERRORMSG_00508 = "00508";//	入力文字(CHAR_VALUE)が最大文字数を超えました。
    public static final String ERRORMSG_00509 = "00509";//	入力件数(CHAR_COUNT)が設定されていません。
    public static final String ERRORMSG_00510 = "00510";//	入力件数(CHAR_COUNT)が正しい数値ではありません。
    
    //验证密码长度
    public static final int PASSWORD_MAX_LENGTH = 32;
	
    public static final int PASSWORD_MIN_LENGTH = 6;
    
    //真实姓名长度
    public static final int NAME_LENGTH = 6;
    
    //码头类型
    public static final int WHARF_TYPE = 0;
    
    //通常图片大小
    public static final int DEFAULT_IMG_SIZE = 2;
    
    //图文详细图片大小
    public static final int TEXT_IMG_SIZE = 10;
    
    //common
    /** 上传图片*/
    public static final String UPLOAD_IMAGE_TYPES = "image/jpg,image/png,image/jpeg,image/gif,application/octet-stream";

    public static final String UPLOAD_APK_TYPES = "application/vnd.android.package-archive";

    /** 上传视频*/
    public static final String UPLOAD_VEDIO_TYPES = "video/mp4";
    
    /** 短信验证类型 模块号 */
    public static final String SMS_NOTIFICATION_CODE = "SMS_205879530";//验证码通知
    public static final String SMS_REGISTERED_CODE = "SMS_206546305";//用户注册
    public static final String SMS_FORGOT_PWD_CODE = "SMS_206546304";//找回密码
    public static final String SMS_UPDATE_PWD_CODE = "SMS_206546302";//修改密码
    public static final String SMS_BIND_PHONE_CODE = "SMS_206536300";//绑定手机
    
    /** 订单通知 模块号 */
    public static final String SMS_ORDER_USER_NOTIF_CODE = "SMS_223193178";//用户下单派单通知
    public static final String SMS_ORDER_MNG_NOTIF_CODE = "SMS_205894704";//用户下单服务通知
    public static final String SMS_ORDER_REFUND_MNG_NOTIF_CODE = "SMS_246205569";//用户下单服务通知
    public static final String SMS_ORDER_REFUND_USER_NOTIF_CODE = "SMS_246100539";//用户下单服务通知

    public static final String SMS_ORDER_OFFLINE_USER_NOTIF_CODE = "SMS_276511134";//线下操作通知


    public static final String SMS_ORDER_OFFLINE_USER_NOTIF_CODE_EXT = "SMS_276502361";//线下操作通知Ext

    //新短信模板

    public static final String SMS_LINE_USER_NOTIFKR_CODE = "SMS_460665501"; //线上通知客人

    public static final String SMS_LINE_USER_NOTIFCZ_CODE = "SMS_460660522";//线上通知船长

    public static final String SMS_OFFLINE_USER_NOTIFKR_CODE = "SMS_460685552";//线下通知客人

    public static final String SMS_OFFLINE_USER_NOTIFCZ_CODE = "SMS_460695536";//线下通知船长



    /** 游艇使用状态颜色 */
    public static final String YACHT_STATUS_TYPE_IDLE = "#4CAF50";//空闲
    public static final String YACHT_STATUS_TYPE_SOLD_ONLINE = "#ff0000";//已售（线上）
    public static final String YACHT_STATUS_TYPE_SOLD_OFFLINE = "#4385f6";//已售（线下）
    public static final String YACHT_STATUS_TYPE_MAINTAIN = "#c9c9c9";//维修

    public static final String YACHT_STATUS_TYPE_YL = "#ffff00";//预留

    public static final String YACHT_STATUS_TYPE_JD = "#9932CD";//线下接待
    
    
    public static final String ERRORMSG_999999 = "999999";//操作不当，请重试
    /** 注册模块 */
    public static final String ERRORMSG_000101 = "000101";//手机号码是空的
    public static final String ERRORMSG_000102 = "000102";//手机号码格式不正确
    public static final String ERRORMSG_000103 = "000103";//手机号码已被注册
    public static final String ERRORMSG_000104 = "000104";//短信验证码发送超时
    public static final String ERRORMSG_000105 = "000105";//短信验证码错误。
    public static final String ERRORMSG_000106 = "000106";//密码是空的。
    public static final String ERRORMSG_000107 = "000107";//密码格式错误。
    public static final String ERRORMSG_000108 = "000108";//该账户不存在，或没有开通相关权限。
    public static final String ERRORMSG_000109 = "000109";//密码错误，请重新输入密码。
    public static final String ERRORMSG_000110 = "000110";//真实姓名是空的，请填写姓名。
    public static final String ERRORMSG_000111 = "000111";//身份证号码是空，请填写身份证号码。
    public static final String ERRORMSG_000112 = "000112";//身份证号码格式错误，请重新填写身份证号码。
    public static final String ERRORMSG_000113 = "000113";//真实姓名与身份证号码不匹配，请重新认证。
    public static final String ERRORMSG_000114 = "000114";//请先勾选需要删除的联系人。
    public static final String ERRORMSG_000115 = "000115";//该联系人信息已被修改，请刷新重试。
    public static final String ERRORMSG_000116 = "000116";//请选择删除的订单
    public static final String ERRORMSG_000117 = "000117";//您没有开通相关权限，请联系管理员。
    public static final String ERRORMSG_000118 = "000118";//该信息不存在或已被其他管理人员修改，请刷新后重试。
    public static final String ERRORMSG_000119 = "000119";//您所设置的时间段已产生订单无法修改，请刷新获取最新信息。

    public static final String ERRORMSG_000127 = "000127";//当前时间已大于您所设置的时间段，请重新设置时间段。

    public static final String ERRORMSG_000128 = "000128";//今日以前的数据都不能修改

    public static final String ERRORMSG_000129 = "000129";//传值有缺失
    public static final String ERRORMSG_000120 = "000120";//没有相关订单的核销信息，请确认该订单是否是核销日期范围内。
    public static final String ERRORMSG_000121 = "000121";//输入的邀请码不存在，请重新输入。
    public static final String ERRORMSG_000122 = "000122";//不能关联同一个账号，请重新输入。
    public static final String ERRORMSG_000123 = "000123";//相关信息已被修改，请刷新重试。
    public static final String ERRORMSG_000124 = "000124";//账户余额不足，请刷新重试。
    public static final String ERRORMSG_000125 = "000125";//请先绑定提现账户再进行提现操作。
    public static final String ERRORMSG_000126 = "000126";//请先勾选需要删除的收藏。
    
    /** 游艇租赁模块     */
    public static final String ERRORMSG_000201 = "000201";//查找不到该游艇，该游艇可能已下架
    public static final String ERRORMSG_000202 = "000202";//找不到该游艇的相关行程信息，请重新选择
    public static final String ERRORMSG_000203 = "000203";//该行程游艇已售空，请返回刷新后重新选择
    public static final String ERRORMSG_000207 = "000207";//请选择支付类型
    public static final String ERRORMSG_000208 = "000208";//订单创建失败，请重试
    public static final String ERRORMSG_000209 = "000209";//阿里证书过期
    public static final String ERRORMSG_000210 = "000210";//预定份数必须大于0
    public static final String ORDER_INFO_000204 = "000204";//订单创建成功
    public static final String ORDER_INFO_000205 = "000205";//该订单状态已被修改，请刷新重试
    public static final String ORDER_INFO_000206 = "000206";//订单取消成功

    /**	WEBAPI　：　RegEntryStatistics　入力集計情報登録 AQPN: RegEntryStatistics Project Name	項目ID */
    public static final String CHAR_VALUE = "CHAR_VALUE";	//	入力文字
    public static final String CHAR_COUNT = "CHAR_COUNT";	//	入力件数

    /**	WEBAPI　：　RegEntryCorrect　正しい入力結果登録 AQPN: RegEntryCorrect Project Name	項目ID */
    public static final String RE_DATAKBN = "DATAKBN";	//	データ管理区分
    public static final String RE_CHAR_VALUE = "CHAR_VALUE";	//	入力文字
    public static final String RE_CHAR_COUNT = "CHAR_COUNT";	//	入力件数

    /**	WEBAPI　：　RegMistakeWord　間違いデータ登録 RMPN: RegMistakeWord  Project Name	項目ID */
    public static final String RMPN_SERVICE_USER_ID ="SERVICE_USER_ID";	//	サービス利用者ID
    public static final String RMPN_SERVICE_AUTH_CODE ="SERVICE_AUTH_CODE";	//	サービス認証コード
    public static final String RMPN_UNMATCHINFODATAS ="UNMATCHINFODATAS";	//	間違いデータレコード
    public static final String RMPN_DATAKBN ="DATAKBN";	//	データ管理区分
    public static final String RMPN_ENTRY_RSLT ="ENTRY_RSLT";	//	エントリー値
    public static final String RMPN_BERIFY_RSLT ="BERIFY_RSLT";	//	ベリ値
    public static final String RMPN_CHAR_DATE ="CHAR_DATE";	//	入力日
    public static final String RMPN_IMAGE ="IMAGE";	//	イメージ

    /**#	間違い情報データベース登録レコードを一括処理 設定値	 */
    public static final String REG_ASSISTACQUISITION = "REG.ASSISTACQUISITION";

    /**#	ベリファイ問合せレコードを一括処理 設定値	 */
    public static final String QUERY_ASSISTACQUISITION = "QUERY.ASSISTACQUISITION";

    /** システムメンテナンス識別子 */
    public static  boolean VERIFY_ANALYSISSERVICE_BATCH_FLAG = false;

    public static  boolean VERIFY_INQUNMATCHANLSFILE_BATCH_FLAG = false;
    
    /** 系统创建排版计划识别子*/
    public static boolean CREATE_SHIFT_SPAN_BATCH_FLAG = false;

    /**	#上传文件 ERROR MESSAGES    */
    public static final String ERRORMSG_EMPTY_FILE = "errors.empty.file";//	{0} 是一个空文件。
    public static final String ERRORMSG_FILE = "errors.file";//  上传的文件有错误，详情请参考错误日志。
    public static final String ERRORMSG_FILE_TYPE = "errors.file.type";//  上传的文件类型错误.
    public static final String ERRORMSG_FILE_TOO_LARGE = "errors.file.too.large";//	附加文件的大小应在 {0} MB 以内。
    public static final String ERRORMSG_CONTENT_TYPE_NOT_ALLOWED = "errors.content.type.not.allowed";//	该文件是无效的内容类型。
    public static final String ERRORMSG_FILE_EXIST = "errors.file.exist";//	{0} 文件不存在。
    public static final String ERRORMSG_DATA_FORMAT2 = "errors.data.format1";//数据格式不正确。
    public static final String ERRORMSG_DATA_LINE = "errors.data.line";//文件内容中的行数不正确。
    public static final String ERRORMSG_EMPTY_DATA = "errors.empty.data";//没有设置数据。
    public static final String ERRORMSG_EMPTY_OPID = "errors.empty.opid";//未设置 OPID {0}。

    /**new String[] {"YYYYMMDD"}     */
    public static final String[] CHAR_DATE_FORMAT = new String[] {"yyyyMMdd"};

    /**Operation system Type */
    public static final String OS_TYPE_WINDOWS = "windows";
    public static final String OS_TYPE_LINUX = "linux";

    /**Download File  Path */
    public static final String FILEPATH_WINDOWS = "src/main/webapp/BATCHFILE/";
    public static final String FILEPATH_LINUX = "/usr/verify/BATCHFILE/";

    /**Encoding Set */
	public static final String ENCODING_SHIFT_JIS = "Shift_JIS";
	public static final String ENCODING_UTF_8 = "UTF-8";
	
	//最大排他次数
	public static final int MAX_UPD_NG = 5;
	
	/*返回错误 */
    public static final String SUCCESS = "SUCCESS";
    public static final String OK = "OK";
    public static final String FAIL = "FAIL";
}
