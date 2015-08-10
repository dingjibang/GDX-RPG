eval(""+load('global.js'));

faceTo(getNPC("1"),RPGObject.FACE_R);
var img= Res.get(Setting.IMAGE_BACKGROUND+"bgnd2.jpg");
CG.push(img);
img.setScale(0.2);
sleep(4000);
img.addAction(Actions.moveTo(200,200,0.3));
print("en");
end();