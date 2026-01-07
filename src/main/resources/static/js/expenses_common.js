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
