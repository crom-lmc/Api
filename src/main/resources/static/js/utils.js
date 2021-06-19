//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

function replaceNull(str) {
    if (str == null || str == undefined) {
        return '';
    }
    return str;
}

function pagination(id, paginationData, callback) {
    const pagination = $('#' + id);
    pagination.empty();
    if (paginationData != 0) {
        const first = paginationData.current == 1 ? "am-disabled" : "";
        const last = paginationData.pages == paginationData.current ? "am-disabled" : "";
        let paginationNode = '<ul class="am-pagination tpl-pagination">';
        paginationNode += '<li class="' + first + '"><a href="javascript:' + callback + '(1)">首页</a></li>';
        paginationNode += '<li class="' + first + '"><a href="javascript:' + callback + '(' + (parseInt(paginationData.current) - 1) + ')">«</a></li>';
        paginationNode += '<li class="am-active"><a href="#">' + paginationData.current + '</a></li>';
        paginationNode += '<li class="' + last + '"><a href="javascript:' + callback + '(' + (parseInt(paginationData.current) + 1) + ')">»</a></li>';
        paginationNode += '<li class="' + last + '"><a href="javascript:' + callback + '(' + paginationData.pages + ')">尾页</a></li>';
        paginationNode += '</ul>';
        pagination.append(paginationNode);
    }
}


//disable all buttons
function disableAllInputs() {
    var input = document.getElementsByTagName("INPUT");
    var iPtr;
    for (iPtr = 0; iPtr < input.length; iPtr++) {
        input[iPtr].disabled = true;
    }
    var select = document.getElementsByTagName("SELECT");
    for (iPtr = 0; iPtr < select.length; iPtr++) {
        select[iPtr].disabled = true;
    }
}

function enableAllInputs() {
    var input = document.getElementsByTagName("INPUT");
    var iPtr;
    for (iPtr = 0; iPtr < input.length; iPtr++) {
        input[iPtr].disabled = false;
    }
    var select = document.getElementsByTagName("SELECT");
    for (iPtr = 0; iPtr < select.length; iPtr++) {
        select[iPtr].disabled = false;
    }
}


//disable all buttons
function disableAllButtons() {
    var aButton = document.getElementsByTagName("INPUT");
    var iPtr;
    for (iPtr = 0; iPtr < aButton.length; iPtr++) {
        if (aButton[iPtr].type == "button" || aButton[iPtr].type == "submit") {
            aButton[iPtr].disabled = true;
        }
    }
    aButton = document.getElementsByTagName("BUTTON");
    for (iPtr = 0; iPtr < aButton.length; iPtr++) {
        aButton[iPtr].disabled = true;
    }
}

function enableAllButtons() {
    var aButton = document.getElementsByTagName("INPUT");
    var iPtr;
    for (iPtr = 0; iPtr < aButton.length; iPtr++) {
        if (aButton[iPtr].type == "button" || aButton[iPtr].type == "submit") {
            aButton[iPtr].disabled = false;
        }
    }
    aButton = document.getElementsByTagName("BUTTON");
    for (iPtr = 0; iPtr < aButton.length; iPtr++) {
        aButton[iPtr].disabled = false;
    }
}

//check on/off all checkboxes
function checkAllCheckBox(checkboxName, isChecked) {
    $('input[name="' + checkboxName + '"][type="checkbox"]').each(function () {
        $(this).prop('checked', isChecked);
    });
}

//jquery must  be ready
function trimAllTextBoxes(form) {
    $('form input[type="text"],form textarea').each(function (index, element) {
        element.value = $.trim(element.value);
    });
}

//format date object with default browser setting
//showTime is boolean: true means show both date and time, else only show date
function formatDateObject(dateObj, showTime) {
    if (!dateObj)
        return "";
    var date = new Date(dateObj);
    if (dateObj.time)
        date = new Date(dateObj.time);
    if (showTime)
        return date.toLocaleString();
    else
        return date.toLocaleDateString();
}

//define jquery common ajax call interface
function callJQueryAjax(sURL, oData, callBackFunc, errorMsg, method, timeout, errorCallBack) {
    $.ajax({
        url: sURL,
        type: method || "post",
        dataType: "json",
        data: oData,
        timeout: timeout || 20000,
        traditional: true,
        error: function (data) {
            if (data.statusText == 'parsererror') {
                window.location.href = "layoutLogin";
            } else {
                if (errorCallBack)
                    errorCallBack();
                if (errorMsg)
                    alert(errorMsg);
                else
                    alert("ERROR:" + data);
            }
        },
        success: function (data, status) {
            if (data.success && data.code == '101') {
                layer.msg('没有访问权限');
            } else if (data.success && data.code == '102') {
                window.location.href = "layoutLogin";
            } else {
                callBackFunc(data);
            }
        }
    });
}

//
/**
 * define jquery ajax upload call interface
 * param data is FormData
 */
function callJQueryAjaxUpload(sURL, data, callBackFunc, errorMsg, timeout, errorCallBack, isSync) {
    $.ajax({
        url: sURL,
        type: "post",
        data: data,
        timeout: timeout || 20000,
        enctype: 'multipart/form-data',
        traditional: true,
        processData: false,  // tell jQuery not to process the data
        contentType: false,   // tell jQuery not to set contentType
        async: !isSync,
        error: function (data) {
            if (errorCallBack)
                errorCallBack();
            if (errorMsg)
                alert(errorMsg);
        },
        success: function (data, status) {
            callBackFunc(data);
        }
    });
}

/**
 * export ajax returned datalist to html table
 * @param dataList
 * @returns html of a table
 */
function exportList2Table(dataList) {
    if (!dataList || dataList.length <= 0)
        return null;
    //export head row
    var html = "";
    var firstItem = dataList[0];
    for (var property in firstItem) {
        if (firstItem.hasOwnProperty(property)) {
            // do stuff
            html += "<td>" + property + "</td>";
        }
    }
    html = "<tr class='info'>" + html + "</tr>\r\n";

    //then export data
    for (iPtr = 0; iPtr < dataList.length; iPtr++) {
        var lineHtml = "";
        for (var key in dataList[iPtr]) {
            if (dataList[iPtr].hasOwnProperty(key)) {
                //console.log(obj[key]);
                var jsonObj = dataList[iPtr][key];
                if (null == jsonObj)
                    jsonObj = "";
                if (jsonObj.time) {
                    var date = jsonObject2Date(jsonObj);
                    jsonObj = date.toUTCString();
                }
                lineHtml += "<td>" + jsonObj + "</td>";
            }
        }
        html += "<tr>" + lineHtml + "</tr>";
    }
    return html;
}

//export list with upper limit
function exportList2TableWithLimit(dataList, upperLimit, btnDownloadHtml) {
    if (!dataList || dataList.length <= 0)
        return null;
    //export head row
    var html = "";

    var firstItem = dataList[0];
    for (var property in firstItem) {
        if (firstItem.hasOwnProperty(property)) {
            // do stuff
            html += "<td>" + property + "</td>";
        }
    }
    html = "<tr class='info'>" + html + "</tr>\r\n";

    //then export data
    var colSize = 0;
    for (iPtr = 0; iPtr < dataList.length; iPtr++) {
        var lineHtml = "";
        for (var key in dataList[iPtr]) {
            if (dataList[iPtr].hasOwnProperty(key)) {
                //console.log(obj[key]);
                var jsonObj = dataList[iPtr][key];
                if (null == jsonObj)
                    jsonObj = "";
                if (jsonObj.time) {
                    var date = jsonObject2Date(jsonObj);
                    jsonObj = date.toUTCString();
                }
                lineHtml += "<td>" + jsonObj + "</td>";
                if (0 == iPtr)
                    colSize++;
            }
        }
        html += "<tr>" + lineHtml + "</tr>";
        if (iPtr >= upperLimit) {
            var downHtml = '<div class="centerDiv">'
                + (dataList.length > upperLimit ? '<div class="warning"> More than ' + upperLimit + ' records found. Please download them or search by other criteria.<br>'
                    + btnDownloadHtml + '</div>' : '')
                + '</div>'
            html += "<tr><td colspan='" + colSize + "'>" + downHtml + "</td></tr>";
            break;
        }
    }
    return html;
}

/**
 *  define a Popup window, this function should be placed at the bottom of HTML page
 */
function PopupWin(popupWinID, htmlContent) {
    this.popWinID = popupWinID;
    var sWinHTML = "<div class=\"popup_shadow\" id=\"shade\" style=\"display:none;\"></div>  \r\n" +
        "<div class=\"popup\" id=\"" + popupWinID + "\" style=\"display:none;\">  \r\n" +
        " <a href=\"javascript:void(0)\" onclick=\"javascript:$('#" + popupWinID + ", #shade').fadeOut('fast');\" class=\"btn_close\">x</a>  \r\n" +
        "    <div class=\"need_account_text\">  " + htmlContent + "\r\n" +
        "    </div>     " +
        "</div> ";
    //document.write(sWinHTML);
    $('body').append(sWinHTML);
}

//define method PopupWin.updateContent
PopupWin.prototype.showContent = function (htmlContent) {
    if (htmlContent)
        $('#' + this.popWinID + ' div').html(htmlContent);
    $(document).find('#' + this.popWinID + ', #shade').fadeIn('slow');
};

PopupWin.prototype.hide = function () {
    $("#" + this.popWinID + ", #shade").fadeOut('fast');
};


function showPopup(popupWinID, htmlContent) {
    var loadingHTML = '<div class="popup_shadow" id="shade" style="display:none;"></div> ' +
        '<div class="popup" id="' + popupWinID + '" style="display:none;">  \r\n' +
        ' <a href="javascript:void(0)" onclick="javascript:$(\'#' + popupWinID + ', #shade\').fadeOut(\'fast\');" class="btn_close">x</a>  \r\n' +
        '    <div class="need_account_text">  ' + htmlContent + '\r\n' +
        '    </div>     ' +
        '</div> ';
    if (!$('#' + popupWinID).length) {
        $('body').append(loadingHTML);
    }
    $('#' + popupWinID + ', #shade').fadeIn('slow');
}

/**
 * define download controller
 * usage in JS:
 * var dc = new DownloadController(url,form);
 * dc.downloadByIFrame();
 *
 * and in Java Action,
 * //add cookie
 *    Cookie cookie=new Cookie("DownloadID", "1");
 *    httpServletResponse.addCookie(cookie);
 */
function DownloadController(url, form) {
    //build variables
    DownloadController.prototype.frameName = 'downloadFrame';
    DownloadController.prototype.DOWNLOADFLAG = "DOWNLOADFLAG=true";

    //define function to get Frame document object
    // if needCreate = true, it will be created if not exists
    DownloadController.prototype.getFrameDoc = function (needCreate) {
        //build html iframe
        var $iframe, iframe_doc;
        //create iframe if not exists
        if (($iframe = $('#' + this.frameName)).length === 0) {
            if (!needCreate) return null;//don't need to create, so no doc found

            $iframe = $("<iframe id='" + this.frameName + "'  style='display: none' src='about:blank'></iframe>")
                .appendTo("body");
        }
        //get document of iframe
        iframe_doc = $iframe[0].contentWindow || $iframe[0].contentDocument;
        if (iframe_doc.document) {
            iframe_doc = iframe_doc.document;
        }
        return iframe_doc;

    };
    //define call back function of downloading
    DownloadController.prototype.downloadTimerFunc = function () {
        var cookie = getCookie("DownloadID");
        if (cookie != undefined && DownloadController.prototype.downloadCheckTimer != undefined)//cookie found
        {
            clearInterval(DownloadController.prototype.downloadCheckTimer);
            enableAllButtons();
            hideLoading();
            //check return value
            var iframeDoc = DownloadController.prototype.getFrameDoc(false);
            if (null != iframeDoc) {
                var newUrl = new String(iframeDoc.URL);
                if (newUrl.indexOf(DownloadController.prototype.DOWNLOADFLAG) > 0) {
                    //html return, so flush current page with returned HTML
                    document.body.innerHTML = iframeDoc.body.innerHTML;
                }
            }
        }
        return;
    };
    //init members
    //downloadByIFrame(url, data);
    this.downloadByIFrame = function () {
        //start to build object to get all elements data
//		var data = new Object();
        var formletsHTML = "";
        if (null != form) {
            for (var iPtr = 0; iPtr < form.elements.length; iPtr++) {
                var eleType = form.elements[iPtr].getAttribute("type");
                if (eleType != "file")//ignore file input
                {
//					data[form.elements[iPtr].name] = form.elements[iPtr].value;
                    if (eleType == "radio" || eleType == "checkbox") {
                        if (!form.elements[iPtr].checked)//for checkbox or radio button, only add checked items
                            continue;
                    }
                    var eleVals = $(form.elements[iPtr]).val();
                    if (null == eleVals)
                        eleVals = "";
                    if (!$.isArray(eleVals))//convert it an array for one element
                        eleVals = [eleVals];
                    for (var iVal = 0; iVal < eleVals.length; iVal++) {
                        var val = eleVals[iVal];
                        if (val)
                            val = val.replace(/'/g, '&#039;');
                        formletsHTML += "<input type='hidden' name='" + form.elements[iPtr].name + "' value='" + val + "'>";
                    }
                }
            }
        }
        //disable all buttons
        disableAllButtons();
        showLoading();//show loading div
        //build html iframe
        var iframe_doc, iframe_html;
        //get document of iframe
        iframe_doc = this.getFrameDoc(true);
        //append a flag after url
        if (url.indexOf("?") > 0)
            url = url + "&";
        else
            url = url + "?";
        url = url + DownloadController.prototype.DOWNLOADFLAG;

        //build iframe html
        iframe_html = "<html><head></head><body><form method='POST' action='" + url + "'>" +
            formletsHTML +
            "</form></body></html>";
        //iframe_doc.open();
        //iframe_doc.write(iframe_html);
        $(iframe_doc.body).html(iframe_html);
        $(iframe_doc).find('form').submit();

        //set timer to check status by cookie
        removeCookie("DownloadID");
        DownloadController.prototype.downloadCheckTimer = setInterval('DownloadController.prototype.downloadTimerFunc();', 1000);
    };
}


//show loading, based on jquery
function showLoading() {
    var loadingHTML = '<!--popup--><div class="popup_shadow" id="loadingShade" style="display:none;"></div> ' +
        '<div class="popup" id="howtopopup" style="display:none; background: rgba( 255, 255, 255, 1 ) ' +
        '        src : ../layer/theme/default/loading-1.gif ' +
        '        50% 50%  ' +
        '        no-repeat; width:150px; height:80px"> ' +
        ' Loading... ' +
        '</div>';
    if (!$('#howtopopup').length) {
        $('body').append(loadingHTML);
    }
    $('#howtopopup, #loadingShade').fadeIn('slow');
}

function hideLoading() {
    $('#howtopopup, #loadingShade').fadeOut('slow');
}


//date utils
//jsonObj is a json object which contains date info (year, month[0-11], date, hours, minuts, seconds, time[miliseconds])
function jsonObject2Date(jsonObj) {
    if (jsonObj) {
        return new Date(jsonObj.time);
    }
    return null;
}

/**
 *  define a EscapeUtil to escape string
 */
function EscapeUtil() {
    //escape ecma script in the text, return new text
    this.escapeEcmaScript = function (text) {
        var aFromEscape = ["'", "\"", "\\", "/", "\b", "\n", "\t", "\f", "\r"];
        var aToEscape = ["\\'", "\\\"", "\\\\", "\\/", "\\b", "\\n", "\\t", "\\f", "\\r"];
        var newText = new String(text);
        for (var iPtr = 0; iPtr < aFromEscape.length; iPtr++)
            newText = newText.replace(aFromEscape[iPtr], aToEscape[iPtr]);
        return newText;
    };
    this.escapeHtml = function (text) {
        return text
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#039;");
    };
}


/**
 *
 */
function submitForm2Div(formId, divId) {
    $.ajax({ // create an AJAX call...
        data: $('#' + formId).serialize(), // get the form data
        type: $('#' + formId).attr('method'), // GET or POST
        url: $('#' + formId).attr('action'), // the file to call
        success: function (response) { // on success..
            $('#' + divId).html(response); // update the DIV
        }
    });
    return false;
}

//auto uppercase textboxes
if ("undefined" != typeof (jQuery)) {
    $(document).on('blur', "input[type=text].AUTO_UPPERCASE", function () {
        $(this).val(function (_, val) {
            return $.trim(val.toUpperCase());
        });
    });
}

function dateFormat(fmt, date) {
    let ret;
    const opt = {
        "y+": date.getFullYear().toString(),        // 年
        "M+": (date.getMonth() + 1).toString(),     // 月
        "d+": date.getDate().toString(),            // 日
        "H+": date.getHours().toString(),           // 时
        "m+": date.getMinutes().toString(),         // 分
        "s+": date.getSeconds().toString()          // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
    };
    for (let k in opt) {
        ret = new RegExp("(" + k + ")").exec(fmt);
        if (ret) {
            fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
        }
    }
    return fmt;
}

function IsPC() {
    const userAgentInfo = navigator.userAgent;
    const Agents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPod"];
    let flag = true;
    for (let v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    if (window.screen.width >= 768) {
        flag = true;
    }
    return flag;
}