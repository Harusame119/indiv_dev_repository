/**
 * クリア処理
 * クリアボタンを押下した際、入力内容をクリアする
 */
function clearTextBox() {

	// 金額テキストボックスクリア処理
	let objectAmount = document.getElementById("textAmount");
	objectAmount.value = "";

	// 備考テキストボックスクリア処理
	let objectRemarks = document.getElementById("textRemarks");
	objectRemarks.value = "";

}

/**
 * 登録処理
 * 登録ボタン押下時、各種プルダウンの選択項目のキーを隠し項目に設定する
 */
function register() {

	let objectPulStore = document.getElementById("pulStore");
	cosole.log(objectPulStore.value);
	
}