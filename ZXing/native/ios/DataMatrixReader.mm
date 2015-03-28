//
//  DataMatrixReader.mm
//  ZXingWidget
//
//  Created by Romain Pechayre on 6/14/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "DataMatrixReaderObjC.h"
#include "DataMatrixReaderCPP.h"

@implementation DataMatrixReader


- (id) init {
  zxing::datamatrix::DataMatrixReader *reader = new   zxing::datamatrix::DataMatrixReader();
  return [super initWithReader:reader];
}
@end
