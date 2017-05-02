/*
#ifdef USE_NORMAL
    varying vec3 vNormal;
#endif
*/

#ifdef USE_OPACITY
    uniform float matOpacity;
#endif

#ifdef USE_MAP
    uniform sampler2D matMap;
    varying vec2 vUv;
#else
    uniform vec3 matEmissiveColor;
#endif


void main(){

    vec4 color;
    #if defined(USE_MAP)
        color = texture2D(matMap,vUv);
    #else
        color = vec4(matEmissiveColor,1.0);
    #endif

    #if defined(USE_OPACITY)
        color = vec4(color.xyz,color.w*matOpacity);
    #endif
    gl_FragColor = color;

}