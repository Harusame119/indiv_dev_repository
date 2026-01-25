function openNav() {
    document.getElementById("mySidebar").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
	document.getElementById("sidebar-overlay").style.display = "block";
}

function closeNav() {
    document.getElementById("mySidebar").style.width = "0";
    document.getElementById("main").style.marginLeft= "0";
	document.getElementById("sidebar-overlay").style.display = "none";
}

/**
 * 指定されたフォームIDのactionを書き換えて送信する
 * @param {string} formId - 送信対象のフォームID (例: 'form-0')
 * @param {string} actionUrl - 送信先URL
 */
function submitTargetForm(formId, actionUrl) {
    const targetForm = document.getElementById(formId);
    if (targetForm) {
        targetForm.action = actionUrl;
        targetForm.submit();
    } else {
        console.error("Form not found: " + formId);
    }
}