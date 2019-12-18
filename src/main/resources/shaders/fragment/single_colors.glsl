#version 450

const int MAX_LIGHTS_COUNT = 64;

struct Material {
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    vec3 emissive;
    float density;
    float specular_exponent;
    float transparency;
    int illum;
};

struct PointLight {
    vec3 position;
    float a;
    vec3 color;
    float b;
};

struct DirectionalLight {
    vec3 direction;
    float a;
    vec3 color;
    float b;
};

struct SpotLight {
    vec3 position;
    float a;
    vec3 direction;
    float b;
    vec3 color;
    float c;
    float cutoff;
};

layout(std140, binding = 1) uniform LightScene {
    int activePointLights;
    int activeDirectionalLights;
    int activeSpotLights;
    PointLight pointLights[MAX_LIGHTS_COUNT];
    DirectionalLight directionalLights[MAX_LIGHTS_COUNT];
    SpotLight spotLights[MAX_LIGHTS_COUNT];
} lightScene;

in vec3 oPosition;
in vec3 oNormal;
in vec2 oTexCoord;
in vec3 oColors;

out vec4 gl_FragColor;

uniform Material material = {
    { 1, 1, 1 },
    { 1, 1, 1 },
    { 1, 1, 1 },
    { 1, 1, 1 },
    1,
    1,
    1,
    1
};

void main() {
    // ambient
    float ambientStrength = .3;
    float diffuseStrength = .6;
    float specularStrength = .1;

    vec3 ambient = ambientStrength * material.ambient * oColors;
    vec3 objectPosition = normalize(oPosition);
    vec3 objectNormal = normalize(oNormal);
    vec3 eye = -objectPosition;// vector eye -> object

    vec3 res = ambient;
    for (int i = 0; i < lightScene.activePointLights; i++) {
        // diffuse
        vec3 lightToObject = normalize(lightScene.pointLights[i].position - oPosition); // vector object -> light
        float lightToObjectAngle = max(dot(objectNormal, lightToObject), 0.0);
        vec3 diffuse = lightToObjectAngle * diffuseStrength * material.diffuse * oColors;

        // specular
        vec3 lightReflection = normalize(reflect(-lightToObject, objectNormal));// reflection of vector object -> light
        float lightReflectionToEye = pow(max(dot(eye, lightReflection), 0.0), 16);
        vec3 specular = lightReflectionToEye * specularStrength * material.specular * oColors;

        res += (diffuse + specular);
    }
    for (int i = 0; i < lightScene.activeDirectionalLights; i++) {
        // diffuse
        float lightToObjectAngle = max(dot(objectNormal, lightScene.directionalLights[i].direction), 0.0);
        vec3 diffuse = lightToObjectAngle * diffuseStrength * material.diffuse * oColors;

        res += diffuse;
    }
    gl_FragColor = vec4(res, 1.0);
}
