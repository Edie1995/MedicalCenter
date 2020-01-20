function setDateInput() {
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth() + 1; // January is 0!
	var yyyy = today.getFullYear();
	if (dd < 10) {
		dd = '0' + dd
	}
	if (mm < 10) {
		mm = '0' + mm
	}

	today = yyyy + '-' + mm + '-' + dd;
	var lastDay = new Date();
	var dateOffset = 2;
	lastDay.setDate(lastDay.getDate() - dateOffset);
	dd = lastDay.getDate();
	mm = lastDay.getMonth() + 1;
	yyyy = lastDay.getFullYear();
	if (dd < 10) {
		dd = '0' + dd
	}
	if (mm < 10) {
		mm = '0' + mm
	}
	lastDay = yyyy + '-' + mm + '-' + dd;
	document.getElementById("symptomAddDate").setAttribute("max", today);
	document.getElementById("symptomAddDate").setAttribute("min", lastDay);
	document.getElementById("symptomSearchDate").setAttribute("max", today);
	document.getElementById("symptomSearchDate").setAttribute("value", today);
	document.getElementById("symptomAddDate").setAttribute("value", today);
}

function setDateInputTomorrow() {
	var tomorrow = new Date();
	var dd = tomorrow.getDate();
	var mm = tomorrow.getMonth() + 1; // January is 0!

	var yyyy = tomorrow.getFullYear();
	if (dd < 10) {
		dd = '0' + dd
	}
	if (mm < 10) {
		mm = '0' + mm
	}

	var dateOffset = 1;
	tomorrow.setDate(tomorrow.getDate() + dateOffset);
	dd = tomorrow.getDate();
	mm = tomorrow.getMonth() + 1;
	yyyy = tomorrow.getFullYear();
	if (dd < 10) {
		dd = '0' + dd
	}
	if (mm < 10) {
		mm = '0' + mm
	}
	tomorrow = yyyy + '-' + mm + '-' + dd;
	var firstDay = new Date();
	dd = firstDay.getDate();
	mm = firstDay.getMonth() + 1;
	yyyy = firstDay.getFullYear();
	if (dd < 10) {
		dd = '0' + dd
	}
	if (mm < 10) {
		mm = '0' + mm
	}
	firstDay = yyyy + '-' + mm + '-' + dd;
	document.getElementById("startDate").setAttribute("min", firstDay);
	document.getElementById("startDate").setAttribute("value", tomorrow);
}

function buttonClick(valueButton) {
	document.getElementById("docorIdx").value = valueButton.value;
	return true;
}

function setDateInputToday() {
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth() + 1; // January is 0!
	var yyyy = today.getFullYear();
	if (dd < 10) {
		dd = '0' + dd
	}
	if (mm < 10) {
		mm = '0' + mm
	}

	today = yyyy + '-' + mm + '-' + dd;

	document.getElementById("date").setAttribute("min", today);
	document.getElementById("date").setAttribute("value", today);
}
function setDateForChosenPatient() {
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth() + 1; // January is 0!
	var yyyy = today.getFullYear();
	if (dd < 10) {
		dd = '0' + dd
	}
	if (mm < 10) {
		mm = '0' + mm
	}

	today = yyyy + '-' + mm + '-' + dd;
	var lastDay = new Date();
	var dateOffset = 2;
	lastDay.setDate(lastDay.getDate() - dateOffset);
	dd = lastDay.getDate();
	mm = lastDay.getMonth() + 1;
	yyyy = lastDay.getFullYear();
	if (dd < 10) {
		dd = '0' + dd
	}
	if (mm < 10) {
		mm = '0' + mm
	}
	lastDay = yyyy + '-' + mm + '-' + dd;
	document.getElementById("dateMediament").setAttribute("min", today);
	document.getElementById("dateMediament").setAttribute("value", today);
	document.getElementById("symptomDate").setAttribute("max", today);
	document.getElementById("symptomDate").setAttribute("min", lastDay);
	document.getElementById("symptomDate").setAttribute("value", today);
}

function disableButton(visit) {
	if (visit.value.length > 0)
		document.getElementById("addSymptom").disabled = false;
	else
		document.getElementById("addSymptom").disabled = true;
}

function minDateTomorrow() {

	var lastDay = new Date();
	var dateOffset = 1;
	lastDay.setDate(lastDay.getDate() + dateOffset);
	dd = lastDay.getDate();
	mm = lastDay.getMonth() + 1;
	yyyy = lastDay.getFullYear();
	if (dd < 10) {
		dd = '0' + dd
	}
	if (mm < 10) {
		mm = '0' + mm
	}
	lastDay = yyyy + '-' + mm + '-' + dd;
	document.getElementById("scheduleDate").setAttribute("min", lastDay);
	document.getElementById("scheduleDate").setAttribute("value", lastDay);
}

function hourCompare() {
	var time1 = document.getElementById("timeStart").value;
	var time2 = document.getElementById("timeEnd").value;
	var step = document.getElementById("duration").value;

	var timeStart = new Date("01/01/2007 " + time1);
	var timeEnd = new Date("01/01/2007 " + time2);
	var timeDuration = new Date("01/01/2007 " + step);

	var difference = timeEnd - timeStart;
	var diff_result = new Date(difference);
	console.log("difference: " + difference);
	console.log("diff_result: " + diff_result);

	var hourDiff = diff_result.getHours();
	var minuteDiff = diff_result.getMinutes() + hourDiff * 60;
	var minuteStep = timeDuration.getMinutes();

	console.log("time1: " + time1);
	console.log("time2: " + time2);
	console.log("step: " + minuteStep);
	console.log("minDiff: " + minuteDiff);
	console.log("hour: " + hourDiff);

	if (time1 > time2) {
		alert("Godzina początkowa jest większa od końcowej!");
		return false;
	} else if (minuteStep > minuteDiff) {

		alert("Czas trwania wizyty jest większy od dostępnego czasu przyjęć!");
		return false;
	}

	return true;

}

function searchHour(docorIdx) {
	var date = document.getElementById(docorIdx).value;
	console.log(date);
	var listDates = document.getElementById("visitDateList").value;
	console.log(listDates);
	if (listDates.includes(date)) {
		return confirm('Masz już wizytę w tym teminie, czy  na pewno chcesz dodać nową?');
	} else {
		return true;
	}
}

function getFile() {
	document.getElementById("file").click();
}