

varying vec4 vFragPos;
varying vec3 vNormal;

#ifdef USE_MAP
    uniform sampler2D matMap;
    varying vec2 vUv;
#endif

uniform vec3 matAmbientColor;
uniform vec3 matDiffuseColor;
uniform vec3 matSpecularColor;
uniform vec3 matEmissiveColor;
uniform float matOpacity;
uniform float matShininess;

varying vec3 vCameraPosition;
varying vec3 vEyeDir;


#ifndef AMBIENT_LIGHTS
    #define AMBIENT_LIGHTS 0
#endif

#ifndef DIRECTIONAL_LIGHTS
    #define DIRECTIONAL_LIGHTS 0
#endif

#ifndef POINT_LIGHTS
    #define POINT_LIGHTS 0
#endif

#ifndef SPOT_LIGHTS
    #define SPOT_LIGHTS 0
#endif

float pow4(float x){
    float x2 = x*x;
    return x2*x2;
}
float pow2(float x){
    return x*x;
}


float irradianceLight( float lightDistance, float cutoffDistance, float decayExponent ){
    return pow( clamp( -lightDistance / cutoffDistance + 1.0,0.,1. ), decayExponent );
    /*
    float distanceFalloff = 1.0 / max( pow( lightDistance, decayExponent ), 0.01 );
    float maxDistanceCutoffFactor = pow2( clamp( 1.0 - pow4( lightDistance / cutoffDistance ),.0,1. ) );
    return distanceFalloff * maxDistanceCutoffFactor;
    */
}

#if AMBIENT_LIGHTS > 0

	struct AmbientLight {
		vec3 ambient;
		float intensity;
	};

	uniform AmbientLight ambientLights[ AMBIENT_LIGHTS ];
#endif

#if DIRECTIONAL_LIGHTS > 0

	struct DirectionalLight {
		vec3 direction;
		vec3 ambient;
		vec3 diffuse;
		vec3 specular;
		float intensity;
	};

	uniform DirectionalLight directionalLights[ DIRECTIONAL_LIGHTS ];
#endif

#if POINT_LIGHTS > 0

	struct PointLight {
		vec3 position;
		vec3 ambient;
		vec3 diffuse;
		vec3 specular;
		float intensity;
		float distance;
		float decay;
	};

	uniform PointLight pointLights[ POINT_LIGHTS ];
#endif

#if SPOT_LIGHTS > 0

	struct SpotLight {
		vec3 position;
		vec3 ambient;
		vec3 diffuse;
		vec3 specular;
		float intensity;
		float distance;
		float spotExponent;//decay
		float spotCutOff;//angle
		float spotCosCutOff;//cos(angle)
		vec3 spotDirection;//must be normalized
		float penumbra;//percent of spotCutOff angle
	};

	uniform SpotLight spotLights[ SPOT_LIGHTS ];
#endif

void main(){

        vec4 mapText;

        #if defined(USE_MAP)
            mapText = texture2D(matMap,vUv);
        #endif

        vec3 E,L,R,D,lightDir;
        float lambertTerm;
        float brightness;
        vec3 intensityAmbient = vec3(0.f,0.f,0.f);
        vec3 intensitySpecular = vec3(0.f,0.f,0.f);
        vec3 intensityDiffuse = vec3(0.f,0.f,0.f);
        #if ( AMBIENT_LIGHTS > 0 )
            AmbientLight ambientLight;
            for ( int i = 0; i < AMBIENT_LIGHTS; i ++ ){
                ambientLight = ambientLights[ i ];
                intensityAmbient += ambientLight.ambient * ambientLight.intensity;
            }
        #endif
        //intensityAmbient *= matAmbientColor;
        //intensityAmbient *= (matEmissiveColor );
        #if ( DIRECTIONAL_LIGHTS > 0 || POINT_LIGHTS > 0 || SPOT_LIGHTS > 0 )
            #if defined(USE_MAP)
                intensityAmbient *= mapText.xyz * vec3(0.8,0.8,0.8 );
            #else
                intensityAmbient *= matDiffuseColor * vec3(0.8,0.8,0.8 );
            #endif

        #else
            #if defined(USE_MAP)
                intensityAmbient *= mapText.xyz;
            #else
                intensityAmbient *= matDiffuseColor;
            #endif
        #endif


        #if ( DIRECTIONAL_LIGHTS > 0 )
            DirectionalLight directionalLight;
            for ( int i = 0; i < DIRECTIONAL_LIGHTS; i ++ ){
                directionalLight = directionalLights[ i ];
                lightDir = normalize(directionalLight.direction);
                lambertTerm = max(dot(vNormal,lightDir),0.0);
                if(lambertTerm>0.0){
                    intensityDiffuse += lambertTerm * directionalLight.diffuse * directionalLight.intensity;
                    //--------------------------------------------------------------------------------------
                    E = normalize(vEyeDir + vCameraPosition);
                    R = normalize(reflect(-lightDir,vNormal));
                    intensitySpecular += directionalLight.specular * pow(max(dot(R,E),0.0),matShininess) * directionalLight.intensity;
                }
            }
        #endif


        #if ( POINT_LIGHTS > 0 )
            PointLight pointLight;
            for ( int i = 0; i < POINT_LIGHTS; i ++ ){
                pointLight = pointLights[ i ];
                lightDir = pointLight.position - vFragPos.xyz;
                //-------------------------------------------------------------------------------
                brightness = irradianceLight(length(lightDir),pointLight.distance,pointLight.decay);
                if(brightness>0.0){
                    float factorDiffuse = max(dot(vNormal,normalize(lightDir)),0.0);
                    //-------------------------------------------------------------------------------
                    if(factorDiffuse>0.0)intensityDiffuse += brightness * factorDiffuse * pointLight.diffuse * pointLight.intensity;
                    //--------------------------------------------------------------------------------------
                    E = vEyeDir;
                    R = normalize(reflect(-lightDir,vNormal));
                    intensitySpecular += brightness * pointLight.specular * pow(max(dot(R,E),0.0),matShininess) * pointLight.intensity;
                }
            }
        #endif
        #if ( SPOT_LIGHTS > 0 )
            SpotLight spotLight;
            for ( int i = 0; i < SPOT_LIGHTS; i ++ ){
                spotLight = spotLights[ i ];
                lightDir = spotLight.position - vFragPos.xyz;
                L = normalize(lightDir);
                D = spotLight.spotDirection;
                float LdotD = dot(-L,D);
                if(LdotD>spotLight.spotCosCutOff){
                    brightness = irradianceLight(length(lightDir),spotLight.distance,spotLight.spotExponent);
                    if(brightness>0.0){
                        lambertTerm = max(dot(vNormal,L),0.0);
                        if(lambertTerm>0.0){
                            intensityDiffuse += brightness * lambertTerm * spotLight.diffuse * spotLight.intensity;
                        }
                        E = vEyeDir;
                        R = reflect(-L,vNormal);
                        intensitySpecular += brightness * spotLight.specular * pow(max(dot(R,E),0.0),matShininess) * spotLight.intensity;
                    }
                }else{

                }
            }
        #endif

        #if defined(USE_MAP)
            intensityDiffuse *= mapText.xyz;
        #else
            intensityDiffuse  *= matDiffuseColor;
        #endif

        intensitySpecular *= matSpecularColor;
        intensitySpecular = clamp(intensitySpecular,0.,1.);

        vec3 finalColor = intensityAmbient + intensityDiffuse + intensitySpecular;

        #if defined(USE_MAP)
            gl_FragColor = vec4(finalColor,matOpacity * mapText.w);
        #else
            gl_FragColor = vec4(finalColor,matOpacity);
        #endif
}