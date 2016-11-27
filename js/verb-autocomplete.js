$(function(){
  var verbs = [
	{ value: 'aban', data: 'aban.html'},
	{ value: 'abart', data: 'abart-v.html'},
	{ value: 'abartıl', data: 'abartıl-v.html'},
	{ value: 'acı', data: 'acı-v.html'},
	{ value: 'acık', data: 'acık-v.html'},
	{ value: 'acındır', data: 'acındır-v.html'},
	{ value: 'ada', data: 'ada-v.html'},
	{ value: 'ak', data: 'ak-v.html'},
	{ value: 'aksa', data: 'aksa-v.html'},
	{ value: 'akset', data: 'akset-v.html'},
	{ value: 'aksır', data: 'aksır-v.html'},
	{ value: 'aktar', data: 'aktar-v.html'},
	{ value: 'al', data: 'al-v.html'},
	{ value: 'aldat', data: 'aldat-v.html'},
	{ value: 'aldır', data: 'aldır-v.html'},
	{ value: 'algıla', data: 'algıla-v.html'},
	{ value: 'alçal', data: 'alçal-v.html'},
	{ value: 'alın', data: 'alın-v.html'},
	{ value: 'alış', data: 'alış-v.html'},
	{ value: 'amaçla', data: 'amaçla-v.html'},
	{ value: 'an', data: 'an-v.html'},
	{ value: 'andır', data: 'andır-v.html'},
	{ value: 'anla', data: 'anla-v.html'},
	{ value: 'anlat', data: 'anlat-v.html'},
	{ value: 'anlaş', data: 'anlaş-v.html'},
	{ value: 'anımsa', data: 'anımsa-v.html'},
	{ value: 'ara', data: 'ara-v.html'},
	{ value: 'arala', data: 'arala-v.html'},
	{ value: 'araştır', data: 'araştır-v.html'},
	{ value: 'art', data: 'art-v.html'},
	{ value: 'arın', data: 'arın-v.html'},
	{ value: 'as', data: 'as-v.html'},
	{ value: 'at', data: 'at-v.html'},
	{ value: 'ata', data: 'ata-v.html'},
	{ value: 'atla', data: 'atla-v.html'},
	{ value: 'atlat', data: 'atlat-v.html'},
	{ value: 'atıl', data: 'atıl-v.html'},
	{ value: 'avun', data: 'avun-v.html'},
	{ value: 'avut', data: 'avut-v.html'},
	{ value: 'ayaklan', data: 'ayaklan-v.html'},
	{ value: 'ayarla', data: 'ayarla-v.html'},
	{ value: 'ayart', data: 'ayart-v.html'},
	{ value: 'aydınlan', data: 'aydınlan-v.html'},
	{ value: 'aydınlat', data: 'aydınlat-v.html'},
	{ value: 'ayrıl', data: 'ayrıl-v.html'},
	{ value: 'ayıkla', data: 'ayıkla-v.html'},
	{ value: 'ayıl', data: 'ayıl-v.html'},
	{ value: 'ayır', data: 'ayır-v.html'},
	{ value: 'az', data: 'az-v.html'},
	{ value: 'azal', data: 'azal-v.html'},
	{ value: 'aç', data: 'aç-v.html'},
	{ value: 'açıkla', data: 'açıkla-v.html'},
	{ value: 'ağla', data: 'ağla-v.html'},
	{ value: 'ağrı', data: 'ağrı-v.html'},
	{ value: 'ağırla', data: 'ağırla-v.html'},
	{ value: 'aş', data: 'aş-v.html'},
	{ value: 'aşın', data: 'aşın-v.html'},
	{ value: 'aşır', data: 'aşır-v.html'},
	{ value: 'bahset', data: 'bahset-v.html'},
	{ value: 'bak', data: 'bak-v.html'},
	{ value: 'bakın', data: 'bakın-v.html'},
	{ value: 'ban', data: 'ban-v.html'},
	{ value: 'barın', data: 'barın-v.html'},
	{ value: 'barış', data: 'barış-v.html'},
	{ value: 'bas', data: 'bas-v.html'},
	{ value: 'bastır', data: 'bastır-v.html'},
	{ value: 'bat', data: 'bat-v.html'},
	{ value: 'bay', data: 'bay-v.html'},
	{ value: 'bayıl', data: 'bayıl-v.html'},
	{ value: 'bağdaş', data: 'bağdaş-v.html'},
	{ value: 'bağla', data: 'bağla-v.html'},
	{ value: 'bağlan', data: 'bağlan-v.html'},
	{ value: 'bağır', data: 'bağır-v.html'},
	{ value: 'bağışla', data: 'bağışla-v.html'},
	{ value: 'başar', data: 'başar-v.html'},
	{ value: 'başla', data: 'başla-v.html'},
	{ value: 'başvur', data: 'başvur-v.html'},
	{ value: 'becer', data: 'becer-v.html'},
	{ value: 'bekle', data: 'bekle-v.html'},
	{ value: 'belgele', data: 'belgele-v.html'},
	{ value: 'belir', data: 'belir-v.html'},
	{ value: 'belirle', data: 'belirle-v.html'},
	{ value: 'belle', data: 'belle-v.html'},
	{ value: 'benze', data: 'benze-v.html'},
	{ value: 'benzeş', data: 'benzeş-v.html'},
	{ value: 'besle', data: 'besle-v.html'},
	{ value: 'betimle', data: 'betimle-v.html'},
	{ value: 'bez', data: 'bez-v.html'},
	{ value: 'beze', data: 'beze-v.html'},
	{ value: 'beğen', data: 'beğen-v.html'},
	{ value: 'bil', data: 'bil-v.html'},
	{ value: 'bildir', data: 'bildir-v.html'},
	{ value: 'bile', data: 'bile-v.html'},
	{ value: 'bileş', data: 'bileş-v.html'},
	{ value: 'bin', data: 'bin-v.html'},
	{ value: 'birik', data: 'birik-v.html'},
	{ value: 'birleş', data: 'birleş-v.html'},
	{ value: 'bit', data: 'bit-v.html'},
	{ value: 'biç', data: 'biç-v.html'},
	{ value: 'boya', data: 'boya-v.html'},
	{ value: 'boz', data: 'boz-v.html'},
	{ value: 'bozul', data: 'bozul-v.html'},
	{ value: 'boğ', data: 'boğ-v.html'},
	{ value: 'boşa', data: 'boşa-v.html'},
	{ value: 'boşal', data: 'boşal-v.html'},
	{ value: 'boşan', data: 'boşan-v.html'},
	{ value: 'boşver', data: 'boşver-v.html'},
	{ value: 'buda', data: 'buda-v.html'},
	{ value: 'bul', data: 'bul-v.html'},
	{ value: 'bula', data: 'bula-v.html'},
	{ value: 'bulan', data: 'bulan-v.html'},
	{ value: 'bulun', data: 'bulun-v.html'},
	{ value: 'buluş', data: 'buluş-v.html'},
	{ value: 'buna', data: 'buna-v.html'},
	{ value: 'bunal', data: 'bunal-v.html'},
	{ value: 'buruş', data: 'buruş-v.html'},
	{ value: 'buyur', data: 'buyur-v.html'},
	{ value: 'böbürlen', data: 'böbürlen-v.html'},
	{ value: 'böl', data: 'böl-v.html'},
	{ value: 'bölüş', data: 'bölüş-v.html'},
	{ value: 'bük', data: 'bük-v.html'},
	{ value: 'bürü', data: 'bürü-v.html'},
	{ value: 'büyü', data: 'büyü-v.html'},
	{ value: 'büyüle', data: 'büyüle-v.html'},
	{ value: 'büyüt', data: 'büyüt-v.html'},
	{ value: 'büz', data: 'büz-v.html'},
	{ value: 'bık', data: 'bık-v.html'},
	{ value: 'bırak', data: 'bırak-v.html'},
	{ value: 'cay', data: 'cay-v.html'},
	{ value: 'coş', data: 'coş-v.html'},
	{ value: 'dadan', data: 'dadan-v.html'},
	{ value: 'dal', data: 'dal-v.html'},
	{ value: 'damla', data: 'damla-v.html'},
	{ value: 'danış', data: 'danış-v.html'},
	{ value: 'daral', data: 'daral-v.html'},
	{ value: 'darıl', data: 'darıl-v.html'},
	{ value: 'davran', data: 'davran-v.html'},
	{ value: 'daya', data: 'daya-v.html'},
	{ value: 'dayan', data: 'dayan-v.html'},
	{ value: 'dağıl', data: 'dağıl-v.html'},
	{ value: 'dağıt', data: 'dağıt-v.html'},
	{ value: 'de', data: 'de-v.html'},
	{ value: 'del', data: 'del-v.html'},
	{ value: 'dene', data: 'dene-v.html'},
	{ value: 'depreş', data: 'depreş-v.html'},
	{ value: 'der', data: 'der-v.html'},
	{ value: 'derle', data: 'derle-v.html'},
	{ value: 'destekle', data: 'destekle-v.html'},
	{ value: 'devir', data: 'devir-v.html'},
	{ value: 'devşir', data: 'devşir-v.html'},
	{ value: 'değ', data: 'değ-v.html'},
	{ value: 'değil', data: 'değil-v.html'},
	{ value: 'değin', data: 'değin-v.html'},
	{ value: 'değiş', data: 'değiş-v.html'},
	{ value: 'deş', data: 'deş-v.html'},
	{ value: 'didin', data: 'didin-v.html'},
	{ value: 'dik', data: 'dik-v.html'},
	{ value: 'dikil', data: 'dikil-v.html'},
	{ value: 'dile', data: 'dile-v.html'},
	{ value: 'dilimle', data: 'dilimle-v.html'},
	{ value: 'din', data: 'din-v.html'},
	{ value: 'dinle', data: 'dinle-v.html'},
	{ value: 'dinlen', data: 'dinlen-v.html'},
	{ value: 'diren', data: 'diren-v.html'},
	{ value: 'diret', data: 'diret-v.html'},
	{ value: 'diril', data: 'diril-v.html'},
	{ value: 'diz', data: 'diz-v.html'},
	{ value: 'dizginle', data: 'dizginle-v.html'},
	{ value: 'dişle', data: 'dişle-v.html'},
	{ value: 'doku', data: 'doku-v.html'},
	{ value: 'dokun', data: 'dokun-v.html'},
	{ value: 'dol', data: 'dol-v.html'},
	{ value: 'dola', data: 'dola-v.html'},
	{ value: 'dolan', data: 'dolan-v.html'},
	{ value: 'dolaş', data: 'dolaş-v.html'},
	{ value: 'doluş', data: 'doluş-v.html'},
	{ value: 'don', data: 'don-v.html'},
	{ value: 'dona', data: 'dona-v.html'},
	{ value: 'donan', data: 'donan-v.html'},
	{ value: 'doy', data: 'doy-v.html'},
	{ value: 'doğ', data: 'doğ-v.html'},
	{ value: 'doğra', data: 'doğra-v.html'},
	{ value: 'doğrul', data: 'doğrul-v.html'},
	{ value: 'dur', data: 'dur-v.html'},
	{ value: 'duy', data: 'duy-v.html'},
	{ value: 'duyumsa', data: 'duyumsa-v.html'},
	{ value: 'duyur', data: 'duyur-v.html'},
	{ value: 'dök', data: 'dök-v.html'},
	{ value: 'dön', data: 'dön-v.html'},
	{ value: 'dönüş', data: 'dönüş-v.html'},
	{ value: 'döv', data: 'döv-v.html'},
	{ value: 'döşe', data: 'döşe-v.html'},
	{ value: 'dür', data: 'dür-v.html'},
	{ value: 'dürt', data: 'dürt-v.html'},
	{ value: 'düz', data: 'düz-v.html'},
	{ value: 'düzel', data: 'düzel-v.html'},
	{ value: 'düzenle', data: 'düzenle-v.html'},
	{ value: 'düş', data: 'düş-v.html'},
	{ value: 'düşle', data: 'düşle-v.html'},
	{ value: 'düşün', data: 'düşün-v.html'},
	{ value: 'edin', data: 'edin-v.html'},
	{ value: 'efkarlan', data: 'efkarlan-v.html'},
	{ value: 'ek', data: 'ek-v.html'},
	{ value: 'ekle', data: 'ekle-v.html'},
	{ value: 'ele', data: 'ele-v.html'},
	{ value: 'eleştir', data: 'eleştir-v.html'},
	{ value: 'elle', data: 'elle-v.html'},
	{ value: 'elver', data: 'elver-v.html'},
	{ value: 'em', data: 'em-v.html'},
	{ value: 'emret', data: 'emret-v.html'},
	{ value: 'engelle', data: 'engelle-v.html'},
	{ value: 'er', data: 'er-v.html'},
	{ value: 'eri', data: 'eri-v.html'},
	{ value: 'eriş', data: 'eriş-v.html'},
	{ value: 'ertele', data: 'ertele-v.html'},
	{ value: 'es', data: 'es-v.html'},
	{ value: 'esirge', data: 'esirge-v.html'},
	{ value: 'eski', data: 'eski-v.html'},
	{ value: 'esne', data: 'esne-v.html'},
	{ value: 'et', data: 'et-v.html'},
	{ value: 'etkile', data: 'etkile-v.html'},
	{ value: 'evir', data: 'evir-v.html'},
	{ value: 'evlen', data: 'evlen-v.html'},
	{ value: 'eyle', data: 'eyle-v.html'},
	{ value: 'ez', data: 'ez-v.html'},
	{ value: 'ezberle', data: 'ezberle-v.html'},
	{ value: 'eğ', data: 'eğ-v.html'},
	{ value: 'eğit', data: 'eğit-v.html'},
	{ value: 'eğle', data: 'eğle-v.html'},
	{ value: 'eğlen', data: 'eğlen-v.html'},
	{ value: 'eşele', data: 'eşele-v.html'},
	{ value: 'feshet', data: 'feshet-v.html'},
	{ value: 'fingirde', data: 'fingirde-v.html'},
	{ value: 'frenle', data: 'frenle-v.html'},
	{ value: 'fırla', data: 'fırla-v.html'},
	{ value: 'fısılda', data: 'fısılda-v.html'},
	{ value: 'fıttır', data: 'fıttır-v.html'},
	{ value: 'fışkır', data: 'fışkır-v.html'},
	{ value: 'gammazla', data: 'gammazla-v.html'},
	{ value: 'geber', data: 'geber-v.html'},
	{ value: 'gecele', data: 'gecele-v.html'},
	{ value: 'gecik', data: 'gecik-v.html'},
	{ value: 'gel', data: 'gel-v.html'},
	{ value: 'geliş', data: 'geliş-v.html'},
	{ value: 'genişle', data: 'genişle-v.html'},
	{ value: 'ger', data: 'ger-v.html'},
	{ value: 'gerek', data: 'gerek-v.html'},
	{ value: 'gereksin', data: 'gereksin-v.html'},
	{ value: 'gerile', data: 'gerile-v.html'},
	{ value: 'gerin', data: 'gerin-v.html'},
	{ value: 'gerçekleş', data: 'gerçekleş-v.html'},
	{ value: 'getir', data: 'getir-v.html'},
	{ value: 'gevşe', data: 'gevşe-v.html'},
	{ value: 'gez', data: 'gez-v.html'},
	{ value: 'gezin', data: 'gezin-v.html'},
	{ value: 'geç', data: 'geç-v.html'},
	{ value: 'geçir', data: 'geçir-v.html'},
	{ value: 'geğir', data: 'geğir-v.html'},
	{ value: 'gider', data: 'gider-v.html'},
	{ value: 'gir', data: 'gir-v.html'},
	{ value: 'giriş', data: 'giriş-v.html'},
	{ value: 'git', data: 'git-v.html'},
	{ value: 'giy', data: 'giy-v.html'},
	{ value: 'gizle', data: 'gizle-v.html'},
	{ value: 'gocun', data: 'gocun-v.html'},
	{ value: 'göm', data: 'göm-v.html'},
	{ value: 'gönder', data: 'gönder-v.html'},
	{ value: 'gör', data: 'gör-v.html'},
	{ value: 'görün', data: 'görün-v.html'},
	{ value: 'görüş', data: 'görüş-v.html'},
	{ value: 'göster', data: 'göster-v.html'},
	{ value: 'götür', data: 'götür-v.html'},
	{ value: 'gözet', data: 'gözet-v.html'},
	{ value: 'gözetle', data: 'gözetle-v.html'},
	{ value: 'gözlemle', data: 'gözlemle-v.html'},
	{ value: 'gözük', data: 'gözük-v.html'},
	{ value: 'göç', data: 'göç-v.html'},
	{ value: 'gücen', data: 'gücen-v.html'},
	{ value: 'gül', data: 'gül-v.html'},
	{ value: 'gülümse', data: 'gülümse-v.html'},
	{ value: 'güreş', data: 'güreş-v.html'},
	{ value: 'güven', data: 'güven-v.html'},
	{ value: 'güçlen', data: 'güçlen-v.html'},
	{ value: 'gıcırda', data: 'gıcırda-v.html'},
	{ value: 'gıdıkla', data: 'gıdıkla-v.html'},
	{ value: 'hafifle', data: 'hafifle-v.html'},
	{ value: 'hakla', data: 'hakla-v.html'},
	{ value: 'hallet', data: 'hallet-v.html'},
	{ value: 'hapset', data: 'hapset-v.html'},
	{ value: 'hapsol', data: 'hapsol-v.html'},
	{ value: 'hapşır', data: 'hapşır-v.html'},
	{ value: 'harca', data: 'harca-v.html'},
	{ value: 'hastalan', data: 'hastalan-v.html'},
	{ value: 'hatırla', data: 'hatırla-v.html'},
	{ value: 'havalan', data: 'havalan-v.html'},
	{ value: 'haykır', data: 'haykır-v.html'},
	{ value: 'hazırla', data: 'hazırla-v.html'},
	{ value: 'haşla', data: 'haşla-v.html'},
	{ value: 'hedefle', data: 'hedefle-v.html'},
	{ value: 'hesapla', data: 'hesapla-v.html'},
	{ value: 'heyecanlan', data: 'heyecanlan-v.html'},
	{ value: 'hisset', data: 'hisset-v.html'},
	{ value: 'homurdan', data: 'homurdan-v.html'},
	{ value: 'hopla', data: 'hopla-v.html'},
	{ value: 'hortla', data: 'hortla-v.html'},
	{ value: 'hoşlan', data: 'hoşlan-v.html'},
	{ value: 'hırpala', data: 'hırpala-v.html'},
	{ value: 'hıçkır', data: 'hıçkır-v.html'},
	{ value: 'ilerle', data: 'ilerle-v.html'},
	{ value: 'ilet', data: 'ilet-v.html'},
	{ value: 'ilgilen', data: 'ilgilen-v.html'},
	{ value: 'iliş', data: 'iliş-v.html'},
	{ value: 'imren', data: 'imren-v.html'},
	{ value: 'in', data: 'in-v.html'},
	{ value: 'inan', data: 'inan-v.html'},
	{ value: 'incel', data: 'incel-v.html'},
	{ value: 'incele', data: 'incele-v.html'},
	{ value: 'incin', data: 'incin-v.html'},
	{ value: 'incit', data: 'incit-v.html'},
	{ value: 'indirge', data: 'indirge-v.html'},
	{ value: 'inle', data: 'inle-v.html'},
	{ value: 'irdele', data: 'irdele-v.html'},
	{ value: 'irkil', data: 'irkil-v.html'},
	{ value: 'ispatla', data: 'ispatla-v.html'},
	{ value: 'iste', data: 'iste-v.html'},
	{ value: 'it', data: 'it-v.html'},
	{ value: 'itele', data: 'itele-v.html'},
	{ value: 'iyileş', data: 'iyileş-v.html'},
	{ value: 'izle', data: 'izle-v.html'},
	{ value: 'iç', data: 'iç-v.html'},
	{ value: 'içer', data: 'içer-v.html'},
	{ value: 'iğren', data: 'iğren-v.html'},
	{ value: 'işe', data: 'işe-v.html'},
	{ value: 'işit', data: 'işit-v.html'},
	{ value: 'işle', data: 'işle-v.html'},
	{ value: 'işlet', data: 'işlet-v.html'},
	{ value: 'kabar', data: 'kabar-v.html'},
	{ value: 'kahrol', data: 'kahrol-v.html'},
	{ value: 'kak', data: 'kak-v.html'},
	{ value: 'kakala', data: 'kakala-v.html'},
	{ value: 'kal', data: 'kal-v.html'},
	{ value: 'kaldır', data: 'kaldır-v.html'},
	{ value: 'kalk', data: 'kalk-v.html'},
	{ value: 'kalkın', data: 'kalkın-v.html'},
	{ value: 'kalkış', data: 'kalkış-v.html'},
	{ value: 'kamaş', data: 'kamaş-v.html'},
	{ value: 'kan', data: 'kan-v.html'},
	{ value: 'kana', data: 'kana-v.html'},
	{ value: 'kap', data: 'kap-v.html'},
	{ value: 'kapa', data: 'kapa-v.html'},
	{ value: 'kapla', data: 'kapla-v.html'},
	{ value: 'kapsa', data: 'kapsa-v.html'},
	{ value: 'kapıl', data: 'kapıl-v.html'},
	{ value: 'kar', data: 'kar-v.html'},
	{ value: 'karala', data: 'karala-v.html'},
	{ value: 'karış', data: 'karış-v.html'},
	{ value: 'karşıla', data: 'karşıla-v.html'},
	{ value: 'karşılaş', data: 'karşılaş-v.html'},
	{ value: 'karşılaştır', data: 'karşılaştır-v.html'},
	{ value: 'kas', data: 'kas-v.html'},
	{ value: 'kat', data: 'kat-v.html'},
	{ value: 'katlan', data: 'katlan-v.html'},
	{ value: 'katıl', data: 'katıl-v.html'},
	{ value: 'kavra', data: 'kavra-v.html'},
	{ value: 'kavur', data: 'kavur-v.html'},
	{ value: 'kavuş', data: 'kavuş-v.html'},
	{ value: 'kay', data: 'kay-v.html'},
	{ value: 'kaybet', data: 'kaybet-v.html'},
	{ value: 'kaybol', data: 'kaybol-v.html'},
	{ value: 'kaydet', data: 'kaydet-v.html'},
	{ value: 'kayna', data: 'kayna-v.html'},
	{ value: 'kaynaklan', data: 'kaynaklan-v.html'},
	{ value: 'kaytar', data: 'kaytar-v.html'},
	{ value: 'kaz', data: 'kaz-v.html'},
	{ value: 'kazan', data: 'kazan-v.html'},
	{ value: 'kaç', data: 'kaç-v.html'},
	{ value: 'kaçın', data: 'kaçın-v.html'},
	{ value: 'kaçır', data: 'kaçır-v.html'},
	{ value: 'kaşı', data: 'kaşı-v.html'},
	{ value: 'kekele', data: 'kekele-v.html'},
	{ value: 'kemir', data: 'kemir-v.html'},
	{ value: 'kes', data: 'kes-v.html'},
	{ value: 'keşfet', data: 'keşfet-v.html'},
	{ value: 'kirala', data: 'kirala-v.html'},
	{ value: 'kirlen', data: 'kirlen-v.html'},
	{ value: 'koca', data: 'koca-v.html'},
	{ value: 'kok', data: 'kok-v.html'},
	{ value: 'kokla', data: 'kokla-v.html'},
	{ value: 'kolla', data: 'kolla-v.html'},
	{ value: 'kon', data: 'kon-v.html'},
	{ value: 'konuş', data: 'konuş-v.html'},
	{ value: 'kop', data: 'kop-v.html'},
	{ value: 'kork', data: 'kork-v.html'},
	{ value: 'koru', data: 'koru-v.html'},
	{ value: 'kov', data: 'kov-v.html'},
	{ value: 'koy', data: 'koy-v.html'},
	{ value: 'koş', data: 'koş-v.html'},
	{ value: 'kucakla', data: 'kucakla-v.html'},
	{ value: 'kullan', data: 'kullan-v.html'},
	{ value: 'kur', data: 'kur-v.html'},
	{ value: 'kurtar', data: 'kurtar-v.html'},
	{ value: 'kurtul', data: 'kurtul-v.html'},
	{ value: 'kuru', data: 'kuru-v.html'},
	{ value: 'kus', data: 'kus-v.html'},
	{ value: 'kutla', data: 'kutla-v.html'},
	{ value: 'kuşat', data: 'kuşat-v.html'},
	{ value: 'küs', data: 'küs-v.html'},
	{ value: 'küçül', data: 'küçül-v.html'},
	{ value: 'küçümse', data: 'küçümse-v.html'},
	{ value: 'kıl', data: 'kıl-v.html'},
	{ value: 'kımılda', data: 'kımılda-v.html'},
	{ value: 'kına', data: 'kına-v.html'},
	{ value: 'kıpırda', data: 'kıpırda-v.html'},
	{ value: 'kır', data: 'kır-v.html'},
	{ value: 'kırp', data: 'kırp-v.html'},
	{ value: 'kırıt', data: 'kırıt-v.html'},
	{ value: 'kırış', data: 'kırış-v.html'},
	{ value: 'kıs', data: 'kıs-v.html'},
	{ value: 'kıskan', data: 'kıskan-v.html'},
	{ value: 'kısıtla', data: 'kısıtla-v.html'},
	{ value: 'kıtırdamak', data: 'kıtırdamak-v.html'},
	{ value: 'kıvran', data: 'kıvran-v.html'},
	{ value: 'kıvır', data: 'kıvır-v.html'},
	{ value: 'kıy', data: 'kıy-v.html'},
	{ value: 'kıyasla', data: 'kıyasla-v.html'},
	{ value: 'kız', data: 'kız-v.html'},
	{ value: 'kızar', data: 'kızar-v.html'},
	{ value: 'kışkırt', data: 'kışkırt-v.html'},
	{ value: 'lütfet', data: 'lütfet-v.html'},
	{ value: 'meraklan', data: 'meraklan-v.html'},
	{ value: 'mıncıkla', data: 'mıncıkla-v.html'},
	{ value: 'mırıldan', data: 'mırıldan-v.html'},
	{ value: 'nitele', data: 'nitele-v.html'},
	{ value: 'oku', data: 'oku-v.html'},
	{ value: 'okşa', data: 'okşa-v.html'},
	{ value: 'ol', data: 'ol-v.html'},
	{ value: 'oluş', data: 'oluş-v.html'},
	{ value: 'ona', data: 'ona-v.html'},
	{ value: 'onar', data: 'onar-v.html'},
	{ value: 'onayla', data: 'onayla-v.html'},
	{ value: 'otur', data: 'otur-v.html'},
	{ value: 'ovala', data: 'ovala-v.html'},
	{ value: 'oy', data: 'oy-v.html'},
	{ value: 'oyala', data: 'oyala-v.html'},
	{ value: 'oyla', data: 'oyla-v.html'},
	{ value: 'oyna', data: 'oyna-v.html'},
	{ value: 'oynat', data: 'oynat-v.html'},
	{ value: 'parla', data: 'parla-v.html'},
	{ value: 'parçala', data: 'parçala-v.html'},
	{ value: 'patla', data: 'patla-v.html'},
	{ value: 'paylaş', data: 'paylaş-v.html'},
	{ value: 'piş', data: 'piş-v.html'},
	{ value: 'planla', data: 'planla-v.html'},
	{ value: 'postala', data: 'postala-v.html'},
	{ value: 'rahatla', data: 'rahatla-v.html'},
	{ value: 'rastla', data: 'rastla-v.html'},
	{ value: 'reddet', data: 'reddet-v.html'},
	{ value: 'sabırsızlan', data: 'sabırsızlan-v.html'},
	{ value: 'sakinleş', data: 'sakinleş-v.html'},
	{ value: 'sakla', data: 'sakla-v.html'},
	{ value: 'sal', data: 'sal-v.html'},
	{ value: 'salla', data: 'salla-v.html'},
	{ value: 'san', data: 'san-v.html'},
	{ value: 'sap', data: 'sap-v.html'},
	{ value: 'sapla', data: 'sapla-v.html'},
	{ value: 'sapta', data: 'sapta-v.html'},
	{ value: 'sar', data: 'sar-v.html'},
	{ value: 'sarar', data: 'sarar-v.html'},
	{ value: 'sark', data: 'sark-v.html'},
	{ value: 'sars', data: 'sars-v.html'},
	{ value: 'sarıl', data: 'sarıl-v.html'},
	{ value: 'sarın', data: 'sarın-v.html'},
	{ value: 'sat', data: 'sat-v.html'},
	{ value: 'sataş', data: 'sataş-v.html'},
	{ value: 'sav', data: 'sav-v.html'},
	{ value: 'savun', data: 'savun-v.html'},
	{ value: 'savur', data: 'savur-v.html'},
	{ value: 'say', data: 'say-v.html'},
	{ value: 'saç', data: 'saç-v.html'},
	{ value: 'saçmala', data: 'saçmala-v.html'},
	{ value: 'sağ', data: 'sağ-v.html'},
	{ value: 'sağla', data: 'sağla-v.html'},
	{ value: 'sağlamlaş', data: 'sağlamlaş-v.html'},
	{ value: 'sendele', data: 'sendele-v.html'},
	{ value: 'ser', data: 'ser-v.html'},
	{ value: 'sergile', data: 'sergile-v.html'},
	{ value: 'serp', data: 'serp-v.html'},
	{ value: 'sertleş', data: 'sertleş-v.html'},
	{ value: 'seslen', data: 'seslen-v.html'},
	{ value: 'sev', data: 'sev-v.html'},
	{ value: 'sevin', data: 'sevin-v.html'},
	{ value: 'seyrekleş', data: 'seyrekleş-v.html'},
	{ value: 'seyret', data: 'seyret-v.html'},
	{ value: 'sez', data: 'sez-v.html'},
	{ value: 'seç', data: 'seç-v.html'},
	{ value: 'seğir', data: 'seğir-v.html'},
	{ value: 'sil', data: 'sil-v.html'},
	{ value: 'silkele', data: 'silkele-v.html'},
	{ value: 'sinirlen', data: 'sinirlen-v.html'},
	{ value: 'sok', data: 'sok-v.html'},
	{ value: 'sokul', data: 'sokul-v.html'},
	{ value: 'soluklan', data: 'soluklan-v.html'},
	{ value: 'sor', data: 'sor-v.html'},
	{ value: 'soruştur', data: 'soruştur-v.html'},
	{ value: 'soy', data: 'soy-v.html'},
	{ value: 'soyun', data: 'soyun-v.html'},
	{ value: 'soyutla', data: 'soyutla-v.html'},
	{ value: 'soğut', data: 'soğut-v.html'},
	{ value: 'sula', data: 'sula-v.html'},
	{ value: 'sulan', data: 'sulan-v.html'},
	{ value: 'sun', data: 'sun-v.html'},
	{ value: 'sus', data: 'sus-v.html'},
	{ value: 'suçla', data: 'suçla-v.html'},
	{ value: 'sök', data: 'sök-v.html'},
	{ value: 'sön', data: 'sön-v.html'},
	{ value: 'söyle', data: 'söyle-v.html'},
	{ value: 'sözleş', data: 'sözleş-v.html'},
	{ value: 'süpür', data: 'süpür-v.html'},
	{ value: 'sür', data: 'sür-v.html'},
	{ value: 'sürt', data: 'sürt-v.html'},
	{ value: 'sürtüş', data: 'sürtüş-v.html'},
	{ value: 'sürükle', data: 'sürükle-v.html'},
	{ value: 'sürün', data: 'sürün-v.html'},
	{ value: 'süz', data: 'süz-v.html'},
	{ value: 'süzül', data: 'süzül-v.html'},
	{ value: 'sıfırla', data: 'sıfırla-v.html'},
	{ value: 'sık', data: 'sık-v.html'},
	{ value: 'sıkıl', data: 'sıkıl-v.html'},
	{ value: 'sıkış', data: 'sıkış-v.html'},
	{ value: 'sına', data: 'sına-v.html'},
	{ value: 'sınırla', data: 'sınırla-v.html'},
	{ value: 'sırala', data: 'sırala-v.html'},
	{ value: 'sıvazla', data: 'sıvazla-v.html'},
	{ value: 'sıyrıl', data: 'sıyrıl-v.html'},
	{ value: 'sızla', data: 'sızla-v.html'},
	{ value: 'sıçra', data: 'sıçra-v.html'},
	{ value: 'sığ', data: 'sığ-v.html'},
	{ value: 'sığın', data: 'sığın-v.html'},
	{ value: 'tak', data: 'tak-v.html'},
	{ value: 'takıl', data: 'takıl-v.html'},
	{ value: 'takın', data: 'takın-v.html'},
	{ value: 'tamamla', data: 'tamamla-v.html'},
	{ value: 'tanı', data: 'tanı-v.html'},
	{ value: 'tanımla', data: 'tanımla-v.html'},
	{ value: 'tanış', data: 'tanış-v.html'},
	{ value: 'tap', data: 'tap-v.html'},
	{ value: 'tara', data: 'tara-v.html'},
	{ value: 'tartış', data: 'tartış-v.html'},
	{ value: 'tat', data: 'tat-v.html'},
	{ value: 'tazele', data: 'tazele-v.html'},
	{ value: 'taş', data: 'taş-v.html'},
	{ value: 'taşı', data: 'taşı-v.html'},
	{ value: 'taşın', data: 'taşın-v.html'},
	{ value: 'tekrarla', data: 'tekrarla-v.html'},
	{ value: 'temizle', data: 'temizle-v.html'},
	{ value: 'terket', data: 'terket-v.html'},
	{ value: 'tiksin', data: 'tiksin-v.html'},
	{ value: 'titre', data: 'titre-v.html'},
	{ value: 'tonlamak', data: 'tonlamak-v.html'},
	{ value: 'toparla', data: 'toparla-v.html'},
	{ value: 'topla', data: 'topla-v.html'},
	{ value: 'tut', data: 'tut-v.html'},
	{ value: 'tutukla', data: 'tutukla-v.html'},
	{ value: 'tutul', data: 'tutul-v.html'},
	{ value: 'tutun', data: 'tutun-v.html'},
	{ value: 'tutuş', data: 'tutuş-v.html'},
	{ value: 'tüken', data: 'tüken-v.html'},
	{ value: 'tüket', data: 'tüket-v.html'},
	{ value: 'türe', data: 'türe-v.html'},
	{ value: 'tüt', data: 'tüt-v.html'},
	{ value: 'tık', data: 'tık-v.html'},
	{ value: 'tıka', data: 'tıka-v.html'},
	{ value: 'tıkla', data: 'tıkla-v.html'},
	{ value: 'tıklat', data: 'tıklat-v.html'},
	{ value: 'tıkırda', data: 'tıkırda-v.html'},
	{ value: 'tırman', data: 'tırman-v.html'},
	{ value: 'ulaş', data: 'ulaş-v.html'},
	{ value: 'um', data: 'um-v.html'},
	{ value: 'unut', data: 'unut-v.html'},
	{ value: 'utan', data: 'utan-v.html'},
	{ value: 'uy', data: 'uy-v.html'},
	{ value: 'uyan', data: 'uyan-v.html'},
	{ value: 'uyar', data: 'uyar-v.html'},
	{ value: 'uyarla', data: 'uyarla-v.html'},
	{ value: 'uygula', data: 'uygula-v.html'},
	{ value: 'uyu', data: 'uyu-v.html'},
	{ value: 'uyuş', data: 'uyuş-v.html'},
	{ value: 'uza', data: 'uza-v.html'},
	{ value: 'uzaklaş', data: 'uzaklaş-v.html'},
	{ value: 'uzan', data: 'uzan-v.html'},
	{ value: 'uzat', data: 'uzat-v.html'},
	{ value: 'uzlaş', data: 'uzlaş-v.html'},
	{ value: 'uç', data: 'uç-v.html'},
	{ value: 'uğra', data: 'uğra-v.html'},
	{ value: 'uğraş', data: 'uğraş-v.html'},
	{ value: 'uğurla', data: 'uğurla-v.html'},
	{ value: 'var', data: 'var-v.html'},
	{ value: 'varol', data: 'varol-v.html'},
	{ value: 'vazgeç', data: 'vazgeç-v.html'},
	{ value: 'ver', data: 'ver-v.html'},
	{ value: 'vur', data: 'vur-v.html'},
	{ value: 'vurgula', data: 'vurgula-v.html'},
	{ value: 'yadsı', data: 'yadsı-v.html'},
	{ value: 'yak', data: 'yak-v.html'},
	{ value: 'yakala', data: 'yakala-v.html'},
	{ value: 'yaklaş', data: 'yaklaş-v.html'},
	{ value: 'yakın', data: 'yakın-v.html'},
	{ value: 'yakış', data: 'yakış-v.html'},
	{ value: 'yalanla', data: 'yalanla-v.html'},
	{ value: 'yalvar', data: 'yalvar-v.html'},
	{ value: 'yan', data: 'yan-v.html'},
	{ value: 'yanaş', data: 'yanaş-v.html'},
	{ value: 'yankılan', data: 'yankılan-v.html'},
	{ value: 'yansı', data: 'yansı-v.html'},
	{ value: 'yanıl', data: 'yanıl-v.html'},
	{ value: 'yanıtla', data: 'yanıtla-v.html'},
	{ value: 'yap', data: 'yap-v.html'},
	{ value: 'yapış', data: 'yapış-v.html'},
	{ value: 'yara', data: 'yara-v.html'},
	{ value: 'yarala', data: 'yarala-v.html'},
	{ value: 'yararlan', data: 'yararlan-v.html'},
	{ value: 'yarat', data: 'yarat-v.html'},
	{ value: 'yardımlaş', data: 'yardımlaş-v.html'},
	{ value: 'yargıla', data: 'yargıla-v.html'},
	{ value: 'yarış', data: 'yarış-v.html'},
	{ value: 'yasakla', data: 'yasakla-v.html'},
	{ value: 'yasla', data: 'yasla-v.html'},
	{ value: 'yat', data: 'yat-v.html'},
	{ value: 'yatır', data: 'yatır-v.html'},
	{ value: 'yatış', data: 'yatış-v.html'},
	{ value: 'yavaşla', data: 'yavaşla-v.html'},
	{ value: 'yavrula', data: 'yavrula-v.html'},
	{ value: 'yay', data: 'yay-v.html'},
	{ value: 'yaygınlaş', data: 'yaygınlaş-v.html'},
	{ value: 'yayımla', data: 'yayımla-v.html'},
	{ value: 'yayınla', data: 'yayınla-v.html'},
	{ value: 'yaz', data: 'yaz-v.html'},
	{ value: 'yazıl', data: 'yazıl-v.html'},
	{ value: 'yaşa', data: 'yaşa-v.html'},
	{ value: 'yaşlan', data: 'yaşlan-v.html'},
	{ value: 'ye', data: 'ye-v.html'},
	{ value: 'yen', data: 'yen-v.html'},
	{ value: 'yer', data: 'yer-v.html'},
	{ value: 'yeral', data: 'yeral-v.html'},
	{ value: 'yerleş', data: 'yerleş-v.html'},
	{ value: 'yet', data: 'yet-v.html'},
	{ value: 'yetin', data: 'yetin-v.html'},
	{ value: 'yetiş', data: 'yetiş-v.html'},
	{ value: 'yeğle', data: 'yeğle-v.html'},
	{ value: 'yinele', data: 'yinele-v.html'},
	{ value: 'yitir', data: 'yitir-v.html'},
	{ value: 'yokla', data: 'yokla-v.html'},
	{ value: 'yol', data: 'yol-v.html'},
	{ value: 'yolla', data: 'yolla-v.html'},
	{ value: 'yor', data: 'yor-v.html'},
	{ value: 'yorumla', data: 'yorumla-v.html'},
	{ value: 'yoğunlaş', data: 'yoğunlaş-v.html'},
	{ value: 'yudumla', data: 'yudumla-v.html'},
	{ value: 'yum', data: 'yum-v.html'},
	{ value: 'yumuşa', data: 'yumuşa-v.html'},
	{ value: 'yumuşat', data: 'yumuşat-v.html'},
	{ value: 'yut', data: 'yut-v.html'},
	{ value: 'yutkun', data: 'yutkun-v.html'},
	{ value: 'yuvarla', data: 'yuvarla-v.html'},
	{ value: 'yönel', data: 'yönel-v.html'},
	{ value: 'yönet', data: 'yönet-v.html'},
	{ value: 'yücel', data: 'yücel-v.html'},
	{ value: 'yükle', data: 'yükle-v.html'},
	{ value: 'yüklen', data: 'yüklen-v.html'},
	{ value: 'yüksel', data: 'yüksel-v.html'},
	{ value: 'yürü', data: 'yürü-v.html'},
	{ value: 'yüz', data: 'yüz-v.html'},
	{ value: 'yüzleş', data: 'yüzleş-v.html'},
	{ value: 'yık', data: 'yık-v.html'},
	{ value: 'yıka', data: 'yıka-v.html'},
	{ value: 'yıl', data: 'yıl-v.html'},
	{ value: 'yırt', data: 'yırt-v.html'},
	{ value: 'yığ', data: 'yığ-v.html'},
	{ value: 'zannet', data: 'zannet-v.html'},
	{ value: 'zayıfla', data: 'zayıfla-v.html'},
	{ value: 'zedele', data: 'zedele-v.html'},
	{ value: 'zorla', data: 'zorla-v.html'},
	{ value: 'zıpla', data: 'zıpla-v.html'},
	{ value: 'zırla', data: 'zırla-v.html'},
	{ value: 'çak', data: 'çak-v.html'},
	{ value: 'çakıl', data: 'çakıl-v.html'},
	{ value: 'çakış', data: 'çakış-v.html'},
	{ value: 'çal', data: 'çal-v.html'},
	{ value: 'çalış', data: 'çalış-v.html'},
	{ value: 'çarp', data: 'çarp-v.html'},
	{ value: 'çat', data: 'çat-v.html'},
	{ value: 'çatla', data: 'çatla-v.html'},
	{ value: 'çatış', data: 'çatış-v.html'},
	{ value: 'çağır', data: 'çağır-v.html'},
	{ value: 'çek', data: 'çek-v.html'},
	{ value: 'çekil', data: 'çekil-v.html'},
	{ value: 'çekin', data: 'çekin-v.html'},
	{ value: 'çekiş', data: 'çekiş-v.html'},
	{ value: 'çel', data: 'çel-v.html'},
	{ value: 'çeliş', data: 'çeliş-v.html'},
	{ value: 'çemkir', data: 'çemkir-v.html'},
	{ value: 'çevir', data: 'çevir-v.html'},
	{ value: 'çevrele', data: 'çevrele-v.html'},
	{ value: 'çisele', data: 'çisele-v.html'},
	{ value: 'çiz', data: 'çiz-v.html'},
	{ value: 'çiğne', data: 'çiğne-v.html'},
	{ value: 'çoğal', data: 'çoğal-v.html'},
	{ value: 'çök', data: 'çök-v.html'},
	{ value: 'çöz', data: 'çöz-v.html'},
	{ value: 'çözümle', data: 'çözümle-v.html'},
	{ value: 'çürü', data: 'çürü-v.html'},
	{ value: 'çık', data: 'çık-v.html'},
	{ value: 'çıkar', data: 'çıkar-v.html'},
	{ value: 'çıkış', data: 'çıkış-v.html'},
	{ value: 'çıldır', data: 'çıldır-v.html'},
	{ value: 'çınla', data: 'çınla-v.html'},
	{ value: 'çırp', data: 'çırp-v.html'},
	{ value: 'çırpın', data: 'çırpın-v.html'},
	{ value: 'çığır', data: 'çığır-v.html'},
	{ value: 'öde', data: 'öde-v.html'},
	{ value: 'öksür', data: 'öksür-v.html'},
	{ value: 'öl', data: 'öl-v.html'},
	{ value: 'ölç', data: 'ölç-v.html'},
	{ value: 'öner', data: 'öner-v.html'},
	{ value: 'öngör', data: 'öngör-v.html'},
	{ value: 'önle', data: 'önle-v.html'},
	{ value: 'öp', data: 'öp-v.html'},
	{ value: 'ör', data: 'ör-v.html'},
	{ value: 'ört', data: 'ört-v.html'},
	{ value: 'örtüş', data: 'örtüş-v.html'},
	{ value: 'öt', data: 'öt-v.html'},
	{ value: 'öv', data: 'öv-v.html'},
	{ value: 'özen', data: 'özen-v.html'},
	{ value: 'özetle', data: 'özetle-v.html'},
	{ value: 'özle', data: 'özle-v.html'},
	{ value: 'öğren', data: 'öğren-v.html'},
	{ value: 'öğret', data: 'öğret-v.html'},
	{ value: 'öğütle', data: 'öğütle-v.html'},
	{ value: 'üre', data: 'üre-v.html'},
	{ value: 'ürk', data: 'ürk-v.html'},
	{ value: 'ürper', data: 'ürper-v.html'},
	{ value: 'üstele', data: 'üstele-v.html'},
	{ value: 'üstlen', data: 'üstlen-v.html'},
	{ value: 'üz', data: 'üz-v.html'},
	{ value: 'üşen', data: 'üşen-v.html'},
	{ value: 'üşü', data: 'üşü-v.html'},
	{ value: 'üşüş', data: 'üşüş-v.html'},
	{ value: 'ıkın', data: 'ıkın-v.html'},
	{ value: 'ılı', data: 'ılı-v.html'},
	{ value: 'ısla', data: 'ısla-v.html'},
	{ value: 'ısmarla', data: 'ısmarla-v.html'},
	{ value: 'ısır', data: 'ısır-v.html'},
	{ value: 'ısıt', data: 'ısıt-v.html'},
	{ value: 'ışı', data: 'ışı-v.html'},
	{ value: 'şaş', data: 'şaş-v.html'},
	{ value: 'şaşır', data: 'şaşır-v.html'},
	{ value: 'şiş', data: 'şiş-v.html'},
	{ value: 'şükret', data: 'şükret-v.html'},
	{ value: 'şımar', data: 'şımar-v.html'}
  ];
  
  // setup autocomplete function pulling from currencies[] array
  $('#autocomplete').autocomplete({
    lookup: verbs,
    onSelect: function (suggestion) {
      var thehtml = suggestion.value + ' -  <a href="frames_web/'+suggestion.data+'"> <strong>(Fiil Çerçevesi)</strong> </a>';
      $('#outputcontent').html(thehtml);
    }
  });
  

});
