/****
 * ajax 请求
 * {{
 * _path: string/项目根目录,
 * _requestPath: string/请求地址,
 * _params: null/传递参数,
 * _dataType: string/json/返回格式,
 * _success: _ajax._success/function/请求成功调用方法,
 * _error: _ajax._error/function/请求失败调用方法,
 * _complete: _ajax._complete/function/请求无论成功或失败调用方法,
 * _data: null/接收成功之后返回的数据,
 * _ajaxAsync: boolean/false/是否进行异步请求,
 * _isCode: boolean/false/是否请求失败,
 * _post: _ajax._post/function/进行post请求,
 * _get: _ajax._get/function/进行get请求
 * }}
 */
var _ajax = {
    /** string/项目根目录 */
    _path: "",
    /** string/请求地址 */
    _requestPath: "",
    /** null/传递参数 */
    _params: null,
    /** string/json/返回格式 */
    _dataType: "json",
    /** function/请求成功调用方法 */
    _success: function(result) {
        console.dir("second success...");
        _ajax._isCode = true;
        _ajax._data = result;
    },
    /** function/请求失败调用方法 */
    _error: function(result) {
        console.dir("second error...");
        _ajax._isCode = false;
    },
    /** function/请求无论成功或失败调用方法 */
    _complete: function(result) { console.dir("second complete..."); },
    /** null/接收成功之后返回的数据 */
    _data: null,
    /** boolean/false/是否请求失败 */
    _isCode: true,
    /** boolean/false/是否进行异步请求 */
    _ajaxAsync: false,
    /** function/进行post请求 */
    _post: function() {
        $.ajaxSettings.async = this._ajaxAsync;
        $.post(this._path + this._requestPath, this._params, function(result) {
            // console.dir("  接收返回的消息... ... ");
            // _ajax._data = result;
        }, this._dataType).success(this._success).error(this._error).complete(this._complete);
        $.ajaxSettings.async = true;
    },
    /** function/进行get请求 */
    _get: function() {
        $.ajaxSettings.async = this._ajaxAsync;
        $.get(this._path + this._requestPath, this._params, function(result) {
            // console.dir("  接收返回的消息... ... ");
            // console.dir(result)
            // _ajax._data = result;
        }, this._dataType).success(this._success).error(this._error).complete(this._complete);
        $.ajaxSettings.async = true;
    },
    /** function/进行ajax请求,无限制返回类型 */
    _other: function() {
        $.ajaxSettings.async = this._ajaxAsync;
        $.ajax({
            url: this._path + this._requestPath,
            async: this._ajaxAsync,
            success: this._success,
            error: this._error,
            complete: this._complete
        });
        $.ajaxSettings.async = true;
    }
}

/**
 * 请求服务器数据
 */
var _ajax_server = {
    /** string/项目根目录 */
    _path: "http://127.0.0.1:8080/blog/",
    _requestPath: "",
    _params: null,
    _data: null,
    _isCode: false,
    _post: function() {
        _ajax._path = this._path;
        _ajax._requestPath = this._requestPath;
        _ajax._params = this._params;
        _ajax._post();
        this._isCode = _ajax._isCode;
        if (_ajax._isCode) {
            this._data = _ajax._data;
            if (this._data.status == 0) {
                this._data = this._data.data;
                console.log("接收到的data值：")
                console.log(this._data)
            } else {
                console.log("--数据接收错误--url:" + this._requestPath + "---" + this._data.msg)
            }
        }
    }
}