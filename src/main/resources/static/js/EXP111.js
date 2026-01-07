/**
 * フォーム送信処理
 * 押下された各ボタンに対応するactionを設定
 */
function submitForm(action) {
	document.getElementById("form1").action = action;
	document.getElementById("form1").submit();
}