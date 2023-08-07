(global["webpackJsonp"] = global["webpackJsonp"] || []).push([["subCartPages/common/vendor"],{

/***/ 319:
/*!************************************************************************!*\
  !*** D:/graduation_design_wx/e-youhui-mall/api/logistics/logistics.js ***!
  \************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(uni) {

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.getLogisticsTrackDetail = exports.getExpressOrderDetail = void 0;
/**
 * 获取用户订单物流详情
 */
var getExpressOrderDetail = function getExpressOrderDetail(orderNumber) {
  return uni.$http.get('/logistics/getExpressOrderDetail/' + orderNumber);
};

/**
 * 获取用户订单物流轨迹详情
 */
exports.getExpressOrderDetail = getExpressOrderDetail;
var getLogisticsTrackDetail = function getLogisticsTrackDetail(expressOrderId) {
  return uni.$http.get('/logistics/getLogisticsTrackDetail/' + expressOrderId);
};
exports.getLogisticsTrackDetail = getLogisticsTrackDetail;
/* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(/*! ./node_modules/@dcloudio/uni-mp-weixin/dist/index.js */ 2)["default"]))

/***/ })

}]);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/subCartPages/common/vendor.js.map