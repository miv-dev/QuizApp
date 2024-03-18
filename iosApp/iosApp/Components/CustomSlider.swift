

import SwiftUI

struct CustomSlider: View {
    var rate: Int
    @State var onChange: (Int) -> Void

    var body: some View {
        GeometryReader(content: { geometry in
            let stepLength = (geometry.size.width - 20) / 10

            VStack{
            HStack(spacing: stepLength - 20) {
                ForEach(0...10, id: \.self){ value in
                    Text("\(value)")
                        .fontWeight(.light)
                        .foregroundColor(Color("AccentColor"))
                        .frame(minWidth: 20, maxWidth: .infinity)
                }

            }
                ZStack(alignment: .leading) {
                ZStack{
                    Rectangle()
                        .foregroundColor( Color("TertiaryColor"))
                        .frame(height: 3)
                        .cornerRadius(1)
                    HStack(spacing: (geometry.size.width - 42) / 10) {
                        ForEach(0...10, id: \.self){ value in



                            Rectangle()
                                .foregroundColor(value <= rate ? Color("AccentColor") : Color("TertiaryColor"))
                                .cornerRadius(1)
                                .frame(width:2, height: 10)
                        }

                    }
                }


                    Rectangle()
                        .frame(height: 3)
                        .foregroundColor(Color("AccentColor"))
                        .frame(width:CGFloat(self.rate) * stepLength, height: 2)
                    Capsule()
                        .foregroundColor(Color("AccentColor"))
                        .frame(width: 16, height: 16)
                        .offset(x: CGFloat(self.rate) * stepLength - 8 )
            }
                    .padding(.horizontal, 10)
                    .gesture(DragGesture(minimumDistance: 0)
                        .onChanged({ value in
                            let len = value.location.x

                            let newRate = Int(round(len / stepLength))
                            if  newRate >= 0 && newRate <= 10 {
                                onChange(newRate)
                                
                            }



                        }))
                    .animation(.easeInOut, value: rate)
        }

        })
    }
}
