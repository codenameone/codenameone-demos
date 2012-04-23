#ifndef __DECODER_H__
#define __DECODER_H__

/*
 *  Decoder.h
 *  zxing
 *
 *  Copyright 2010 ZXing authors All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include "ReedSolomonDecoder.h"
#include "GF256.h"
#include "Counted.h"
#include "Array.h"
#include "DecoderResult.h"
#include "BitMatrix.h"

namespace zxing {
namespace qrcode {

class Decoder {
private:
  ReedSolomonDecoder rsDecoder_;

  void correctErrors(ArrayRef<unsigned char> bytes, int numDataCodewords);

public:
  Decoder();
  Ref<DecoderResult> decode(Ref<BitMatrix> bits);
};

}
}

#endif // __DECODER_H__
