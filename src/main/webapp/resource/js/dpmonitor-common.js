/**
 * Created by xiaoning.yue on 2014/5/7.
 */
function alertChanged(label, addClass, content) {
    var $html = label.children();
    label.html($html);
    label.addClass(addClass);
    label.append(content);
}
