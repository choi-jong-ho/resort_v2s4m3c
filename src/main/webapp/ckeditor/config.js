/**
 * @license Copyright (c) 2003-2019, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */
 
CKEDITOR.editorConfig = function( config ) {
  // Define changes to default configuration here. For example:
  // config.language = 'fr';
  // config.uiColor = '#AADC6E';
  config.height = 500; // 편집화면 높이
  //config.uiColor = '#9AB8F3';
  config.uiColor = '#D3D3D3';
  config.enterMode = CKEDITOR.ENTER_BR; //엔터키 태그 1:<p>, 2:<br>, 3:<div>
  config.font_defaultLabel = 'Malgun Gothic'; //기본글씨
  config.font_names = '굴림체/Gulim;돋움체/Dotum;맑은 고딕/Malgun Gothic;';
  config.fontSize_defaultLabel = '14px';
  config.fontSize_sizes = '14/14px;16/16px;20/20px;24/24px;28/28px;36/36px;48/48px;72/72px;';
 
  config.toolbarGroups = [
    { name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
    { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
    { name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
    { name: 'forms', groups: [ 'forms' ] },
    '/',
    { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
    { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
    { name: 'links', groups: [ 'links' ] },
    { name: 'insert', groups: [ 'insert' ] },
    '/',
    { name: 'styles', groups: [ 'styles' ] },
    { name: 'colors', groups: [ 'colors' ] },
    { name: 'tools', groups: [ 'tools' ] },
    { name: 'others', groups: [ 'others' ] },
    { name: 'about', groups: [ 'about' ] }
  ];
 
// Remove some buttons provided by the standard plugins, which are
// not needed in the Standard(s) toolbar.
config.removeButtons = 'Underline,Subscript,Superscript';
 
// Set the most common block elements.
config.format_tags = 'p;h1;h2;h3;pre';
 
// Simplify the dialog windows.
config.removeDialogTabs = 'image:advanced;link:advanced';
  config.filebrowserBrowseUrl = "../ckfinder/ckfinder.html";
  config.filebrowserFlashBrowseUrl = "../ckfinder/ckfinder.html?type=Flash";
  config.filebrowserUploadUrl = "../ckfinder/core/connector/java/connctor.java?command=QuickUpload&type=Files";
  config.filebrowserImageUploadUrl = "../ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images";
  config.filebrowserFlashUploadUrl = "../ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash";
};
  