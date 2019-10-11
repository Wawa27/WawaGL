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

in vec3 aPosition;
in vec3 aNormal;
in vec2 aTexCoord;

uniform sampler2D texture_kd;

uniform Material material = {
vec3(1, 1, 1),
vec3(1, 1, 1),
vec3(1, 1, 1),
vec3(1, 1, 1),
1.0,
1.0,
1.0,
1
};

// FIXME: int doesnt work
layout(std140, binding = 1) uniform LightScene {
    int activePointLights;
    int activeDirectionalLights;
    int activeSpotLights;
    PointLight pointLights[MAX_LIGHTS_COUNT];
    DirectionalLight directionalLights[MAX_LIGHTS_COUNT];
    SpotLight spotLights[MAX_LIGHTS_COUNT];
} lightScene;

layout(location = 0) out vec4 gl_FragColor;

void main() {
    // ambient
    float ambientStrength = 0.1;
    float specularStrength = .5;

    vec3 ambient = ambientStrength * texture(texture_kd, aTexCoord).xyz;
    // todo: shoudln't normalzie ?
    vec3 normal = normalize(aNormal);
    vec3 eye = normalize(-aPosition);// eye is the vector eye -> object

    vec3 res = ambient;
    for (int i = 0; i < lightScene.activePointLights; i++) {
        // diffuse
        vec3 lightToObject = normalize(lightScene.pointLights[i].position - aPosition);
        float lightToObjectAngle = max(dot(normal, lightToObject), 0.0);
        vec3 diffuse = lightToObjectAngle * texture(texture_kd, aTexCoord).xyz;

        // specular
        vec3 r = reflect(-lightToObject, normal);// r is the reflection of vector object -> light
        float reflectionToEye = pow(max(dot(eye, r), 0.0), 32);
        vec3 specular = specularStrength * reflectionToEye * texture(texture_kd, aTexCoord).xyz;

        res += (diffuse + specular) * lightScene.pointLights[i].color;
    }
    for (int i = 0; i < lightScene.activeDirectionalLights; i++) {
        // diffuse
        float lightToObjectAngle = max(dot(normal, lightScene.directionalLights[i].direction), 0.0);
        vec3 diffuse = lightToObjectAngle * lightScene.directionalLights[i].color * texture(texture_kd, aTexCoord).xyz;

        res += diffuse;
    }
    gl_FragColor = vec4(res, 1.0);
}
