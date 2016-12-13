package se.lth.cs.srl.features;

public enum FeatureName {
	PredWord, PredLemma, PredPOS, PredPPOS, PredDeprel, PredLemmaSense, PredFeats, PredValency, PredVerbForm,
    PredParentWord, PredParentPOS, PredParentFeats, PredParentLemma,
    DepSubCat,
	   ChildDepSet, ChildWordSet, ChildLemmaSet, ChildPOSSet, ChildCaseMarkerSet, ChildIsMWE,
	   ArgWord, ArgPOS, ArgPPOS, ArgFeats, ArgCaseMark, ArgVerbForm, ArgDeprel,ArgMWE, ArgFirstPosition,ArgLemma,
	   LeftWord, LeftPOS, LeftPPOS, LeftFeats,
	   RightWord, RightPOS, RightPPOS, RightFeats,
	   LeftSiblingWord, LeftSiblingPOS, LeftSiblingPPOS, LeftSiblingFeats,
	   RightSiblingWord, RightSiblingPOS, RightSiblingPPOS, RightSiblingFeats,
	   POSPath, PPOSPath, DeprelPath,
	   Position, 
	   
		BrownShortPred, BrownShortPredParent, BrownShortArg, BrownShortLeftDep, BrownShortRightDep, BrownShortLeftSibling, BrownShortRightSibling,
		BrownLongPred, BrownLongPredParent, BrownLongArg, BrownLongLeftDep, BrownLongRightDep, BrownLongLeftSibling, BrownLongRightSibling,

}
